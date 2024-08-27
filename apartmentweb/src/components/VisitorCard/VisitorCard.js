import { Header } from "../Common/Header";
import { Footer } from "../Common/Footer";
import { Badge, Button, Card, Form, ListGroup, Modal } from "react-bootstrap";
import { Fragment, useContext, useEffect, useState } from "react";
import APIs, { authApi, endpoints } from "../../configs/APIs";
import { UserContext } from "../../configs/Contexts";
import { Navigate } from "react-router-dom";

const VistorCard = () => {

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
            height: "90vh",
            backgroundColor: "#f8f9fa",
            border: "1px solid #D0D5D9",
            borderRadius: 14,
            padding: 10,
            overflowY: "scroll",

        },
    }

    const [show, setShow] = useState(false)
    const user = useContext(UserContext)
    const [visitor, setVisitor] = useState([])
    const [newVisitor, setNewVisitor] = useState({})


    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const change = (event, field) => {
        setNewVisitor(current => {
            return { ...current, [field]: event.target.value }
        })
    }

    const submitForm = async (e) => {
        e.preventDefault()

        let form = new FormData()
        for (let k in newVisitor)
            form.append(k, newVisitor[k]);
        form.append('userId', user.id)

        try {
            let res = await authApi().post(endpoints['create-visitor'], form, {
                headers: {
                    // 'Content-Type': 'multipart/form-data'
                }
            });
            if (res.status === 200) {
                handleClose()
                alert("Đăng ký thành công, quý khách vui lòng tải lại trang để xem thông tin")
            }
            else
                alert("Đã xảy ra lỗi :((")
        } catch (ex) {
            console.error(ex);
        }
    }

    useEffect(() => {
        if (user !== null) {
            const loadVisitors = async () => {
                try {
                    let res = await authApi().get(endpoints['visitor'](user.id))
                    console.log(res.data)
                    setVisitor(res.data)
                } catch (ex) {
                    console.error(ex)
                }
            }

            loadVisitors()
        }
    }, [user])

    const cancelVisitor = async (e, vId) => {
        e.preventDefault();
        try{
            let res = await APIs.delete(endpoints['delete-visitor'](vId))
            alert("Hủy đăng ký thành công, quý khách vui lòng tải lại trang để xem cập nhật")
        }catch(ex){
            console.error(ex)
        }
    }


    if (user === null)
        return <Navigate to="/login" />

    return (
        <>
            <Header />

            <div style={styles.container}>

                <div style={styles.container_child} >
                    <Button className="mb-3" onClick={handleShow}>Đăng ký thẻ mới</Button>
                    {visitor.map(v => <Fragment key={v.id}>

                        <Card className="mt-1 mb-3">
                            <Card.Header>
                                Thông tin thẻ
                            </Card.Header>
                            <Card.Body>
                                <ListGroup variant="flush">
                                    <ListGroup.Item>Chủ thẻ: {v.name}</ListGroup.Item>
                                    <ListGroup.Item>Quan hệ với cư dân: {v.relation}</ListGroup.Item>
                                    <ListGroup.Item>Ngày đăng ký: {v.createdDate}</ListGroup.Item>
                                    <ListGroup.Item>
                                        {v.status === "waiting" ? <>
                                            Trạng thái: <Badge bg="secondary">đang chờ</Badge>
                                        </> : <>
                                            Trạng thái: <Badge bg="success">đã xác nhận</Badge>
                                        </>}
                                    </ListGroup.Item>
                                </ListGroup>
                            </Card.Body>
                            {v.status === "waiting" ? <>
                                <Card.Footer>
                                    <Button className="btn-danger" onClick={(e)=>cancelVisitor(e,v.visitorId)}>Hủy đăng ký</Button>
                                </Card.Footer>
                            </> : <></>}

                        </Card>
                    </Fragment>)}
                </div>
            </div>


            <Footer />


            <Modal show={show} onHide={handleClose} backdrop="static" keyboard={false}>
                <Modal.Header closeButton>
                    <Modal.Title>Đăng ký thẻ mới</Modal.Title>
                </Modal.Header>
                <Modal.Body>

                    <Form id="form" onSubmit={submitForm}>
                        <Form.Group className="mb-3" controlId="name">
                            <Form.Label>Tên chủ thẻ</Form.Label>
                            <Form.Control onChange={e => change(e, "name")} type="text" placeholder="Nguyễn Văn A" />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="relation">
                            <Form.Label>Quan hệ với cư dân</Form.Label>
                            <Form.Control onChange={e => change(e, "relation")} type="text" placeholder="Vd: bạn bè, anh, em..." />
                        </Form.Group>
                        <Button type="submit" variant="primary" onClick={submitForm}>
                            Đăng ký
                        </Button>
                    </Form>

                </Modal.Body>
                
            </Modal>
        </>
    )
}
export default VistorCard;