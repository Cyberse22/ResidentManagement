import { Fragment, useContext, useEffect, useRef, useState } from "react"
import { Footer } from "../Common/Footer"
import { Header } from "../Common/Header"
import { UserContext } from "../../configs/Contexts"
import { Navigate, useNavigate, useSearchParams } from "react-router-dom"
import { authApi, endpoints } from "../../configs/APIs"
import { Alert, Button, Form, Image, Modal, Spinner, Table } from "react-bootstrap"

const UserInvoice = () => {
    const styles = {
        container: {
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            height: "100vh",
            backgroundImage: "url('https://ttcland.vn/pictures/catalog/bg-menu-1.jpg')",
        },
        container_child: {
            width: "90vw",
            height: "70vh",
            backgroundColor: "#f8f9fa",
            border: "1px solid #D0D5D9",
            borderRadius: 14,
            padding: 10,
            overflowY: "scroll",

        },
    }

    const user = useContext(UserContext)
    const [invoices, setInvoices] = useState([])
    const [paymentProve, setPaymentProve] = useState('')
    const [show, setShow] = useState(false);
    const [show2, setShow2] = useState(false)
    const [invoiceId, setInvoiceId] = useState()
    const [q,] = useSearchParams();
    const nav = useNavigate()
    const img = useRef()

    const handleClose = () => setShow(false);
    const handleShow = (img) => {
        setPaymentProve(img)
        setShow(true);
    }
    const handleClose2 = () => setShow2(false)
    const handleShow2 = (id) => {
        setInvoiceId(id)
        setShow2(true)
    }

    const loadInvoices = async () => {
        try {

            let url = endpoints['invoices'](user.id)

            let status = q.get('status')
            if (status) {
                url = `${url}?status=${status}`
                console.log(url)
            }

            const res = await authApi().get(url)
            console.log(res.data)
            setInvoices(res.data)


        } catch (ex) {
            console.error(ex)
        }
    }

    useEffect(() => {
        loadInvoices()
    }, [q])

    const filter = (status) => {
        nav(`/invoices/?status=${status}`)
    }

    const formatCurrency = (amount) => {
        return amount.toLocaleString('vi-VN') + " VND";
    };

    const confirmPayment = async (e, invoiceId) => {
        e.preventDefault()

        let form = new FormData()
        form.append('file', img.current.files[0]);

        if (img.current.files[0] === null || img.current.files[0] === undefined) {
            alert("Vui lòng chọn ảnh minh chứng !")
            return nav("/invoices")
        }

        try {
            console.log("line 92",img.current.files[0])
            let res = await authApi().post(endpoints['invoice-pay'](invoiceId), form, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            if (res.status === 200)
                {
                    alert("Thanh toán thành công, cư dân vui lòng tải lại trang để xem cập nhật")
                    handleClose2()
                }
        } catch (ex) {
            console.error(ex);
        }
    }

    if (user === null)
        return <Navigate to="/login" />
    return (
        <>
            <Header />
            <div style={styles.container}>
                <div style={styles.container_child}>
                    <Alert variant="warning">&#10071; Cư dân vui lòng thanh toán đúng hạn !</Alert>

                    <div>
                        <Button className="btn-danger m-2" onClick={() => filter("unpaid")}>Chưa thanh toán</Button>
                        <Button className="btn-info m-2" onClick={() => filter("waiting")}>Đang chờ</Button>
                        <Button className="btn-success m-2" onClick={() => filter("paid")}>Đã thanh toán</Button>
                    </div>

                    {invoices.length === 0 ? <>
                        <Alert variant="white">&#10071; Không tìm thấy hóa đơn của bạn <Spinner animation="border" variant="primary" size="sm" /></Alert>

                    </> : <>
                        <Table bordered size="xl">
                            <thead>
                                <tr style={{ height: "40px" }}>
                                    <th style={{ paddingLeft: "100px" }}>Id</th>
                                    <th>Loại hóa đơn</th>
                                    <th>Chi phí</th>
                                    <th>Từ ngày</th>
                                    <th>Đến ngày</th>
                                    <th>Trạng thái</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                {invoices.map(i => <Fragment key={i.id}>
                                    <tr style={{ height: "50px" }}>
                                        <td style={{ paddingLeft: "100px" }}>{i.id}</td>
                                        <td>{i.name}</td>
                                        <td>{formatCurrency(i.amount)}</td>
                                        <td>{i.createdDate}</td>
                                        <td>{i.dueDate}</td>
                                        <td>
                                            {i.status === "unpaid" ? (<span className="text-danger">Chưa thanh toán</span>)
                                                : i.status === "waiting" ? (<span className="text-primary">Đang chờ xác nhận</span>)
                                                    : (<span className="text-success">Đã thanh toán</span>)}
                                        </td>
                                        <td>
                                            {i.paymentProve !== null ? <>
                                                <Button className="btn btn-info" onClick={() => handleShow(i.paymentProve)}>Ảnh minh chứng</Button>
                                            </> : <>
                                                <Button className="btn btn-primary" onClick={() => handleShow2(i.id)}>Thanh toán</Button>
                                            </>}
                                        </td>
                                    </tr>
                                </Fragment>)}
                            </tbody>

                        </Table>
                    </>}

                </div>
            </div>
            <Footer />


            <Modal show={show} onHide={handleClose} centered >
                <Modal.Body>
                    <Image src={paymentProve} fluid rounded />
                </Modal.Body>
            </Modal>

            <Modal show={show2} onHide={handleClose2} centered backdrop="static">
                <Modal.Header closeButton>

                </Modal.Header>
                <Modal.Body closeButton>
                    <Alert variant="info">Thanh toán theo QR sau, và upload hình ảnh minh chứng.</Alert>
                    <Image src={"https://res.cloudinary.com/dwdvnztnn/image/upload/v1719339305/qr1_adinto.jpg"} fluid rounded />

                    <Form.Group className="mt-3" controlId="confirm">
                        <Form.Control ref={img} type="file" accept=".png,.jpg" />
                    </Form.Group>
                </Modal.Body>
                <Modal.Footer>
                    <Button type="submit" variant="primary" onClick={(e)=>confirmPayment(e,invoiceId)}>
                        Xác nhận thanh toán
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    )
}
export default UserInvoice