import { Fragment, useContext, useEffect, useState } from "react";
import { Alert, Button, Container, Form, ListGroup, Modal, Spinner, Table } from "react-bootstrap";
import { Navigate } from "react-router-dom";
import { authApi, endpoints } from "../../configs/APIs";
import { UserContext } from "../../configs/Contexts";
import { Header } from "../Common/Header";
import { Footer } from "../Common/Footer";

const Feedback = () => {
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
    const [showFeedback, setShowFeedback] = useState(false)
    const user = useContext(UserContext)
    const [feedback, setFeedback] = useState([])
    const [content, setContent] = useState('')

    const handleCloseFeedback = () => setShowFeedback(false);
    const handleShowFeedback = () => setShowFeedback(true);

    const sendFeedback = async (e) => {
        e.preventDefault();
        try {
            await authApi().post(endpoints['create-feedback'], { content })
            setContent('')
            await loadFeedbacks()
            handleCloseFeedback()
        } catch (err) {
            console.error('Create feedback error: ' + err)
        }
    }

    const loadFeedbacks = async () => {
        try {
            let res = await authApi().get(endpoints['feedback'])
            console.log(res.data)
            setFeedback(res.data)
        } catch (ex) {
            console.error("error fetching feedback", ex)
        }
    }

    useEffect(() => {
        if (user !== null) {
            loadFeedbacks()
        }
    }, [user])


    if (user === null)
        return <Navigate to="/login" />

    return (
        <>
            <Header />
            <div style={styles.container}>
                <div style={styles.container_child} >
                    <Alert variant="success">Tất cả phản hồi của cư dân</Alert>
                    <Button className="mb-2" onClick={handleShowFeedback}>
                        Viết phản hồi
                    </Button>

                    <Table bordered size="xl">
                        <thead>
                            <tr style={{ height: "40px" }}>
                                <th style={{ paddingLeft: "100px" }}>Id</th>
                                <th>Cư dân</th>
                                <th>Ngày gửi</th>
                                <th>Nội dung</th>
                                <th>Trạng thái</th>
                                
                            </tr>
                        </thead>
                        <tbody>
                            {feedback.map((entry, index) => <Fragment key={entry.id}>
                                <tr style={{ height: "50px" }}>
                                    <td style={{ paddingLeft: "100px" }}>{entry.id}</td>
                                    <td>{entry.firstName} {entry.lastName}</td>
                                    <td>{entry.createdDate}</td>
                                    <td>{entry.content}</td>
                                    <td>
                                        {entry.status === 0 ? (<span className="text-danger">Đang chờ xử lý</span>)
                                            : (<span className="text-primary">Đã xử lý</span>)}

                                    </td>
                                    
                                </tr>
                            </Fragment>)}
                        </tbody>

                    </Table>


                </div>
            </div>




            <Modal show={showFeedback} onHide={handleCloseFeedback}>
                <Modal.Header closeButton>
                    <Modal.Title>Đóng góp ý kiến</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form className="mt-3" onSubmit={sendFeedback}>
                        <Form.Group controlId="formFeedback">
                            <Form.Label>Ý kiến của bạn</Form.Label>
                            <Form.Control
                                required
                                as="textarea"
                                rows={3}
                                placeholder="Viết ý kiến của bạn"
                                value={content}
                                onChange={(e) => setContent(e.target.value)}
                            />
                            <Form.Control.Feedback type="invalid">
                                Vui lòng nhập phản hồi của bạn
                            </Form.Control.Feedback>
                        </Form.Group>
                        <Button type="submit" className="mt-2" >Gửi phản hồi</Button>
                    </Form>
                </Modal.Body>
            </Modal>

            <Footer/>


        </>
    )

}
export default Feedback