import { Navigate, useNavigate, useParams } from "react-router-dom"
import { Footer } from "../Common/Footer"
import { Header } from "../Common/Header"
import { Badge, Button, Nav, Spinner, Table } from "react-bootstrap"
import { Fragment, useContext, useEffect, useState } from "react"
import APIs, { authApi, endpoints } from "../../configs/APIs"
import { UserContext } from "../../configs/Contexts"



const SurveyDetail = () => {
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
    const { id } = useParams()
    const [surveyDetail, setSurveyDetail] = useState(null)
    const user = useContext(UserContext)
    const [listAnswer, setListAnswer] = useState([])
    const nav = useNavigate();

    const loadSurveyDetail = async () => {
        try {
            let res = await authApi().get(endpoints['surveys-detail'](id))
            setSurveyDetail(res.data)
        } catch (ex) {
            console.error(ex)
        }
    }

    // ****************
    const answerTmp = {
        "content": "1",
        "userId": null,
        "questionId": null
    }

    const loadListAnswer = async () => {
        const answers = surveyDetail.questions.map(i => ({ ...answerTmp, questionId: i.id, userId: user.id }))
        setListAnswer(answers)
    }

    useEffect(() => {
        loadSurveyDetail();
    }, [])

    useEffect(() => {
        if (surveyDetail) {
            loadListAnswer();
        }
    }, [surveyDetail])


    const handleContentChange = (event, index) => {
        if (listAnswer.length !== 0) {
            listAnswer[index].content = event.target.value
            console.log("Dữ liệu của listAnswer :", listAnswer)
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault()
        try {
            let res = authApi().post(endpoints['surveys-answers'](id), { listAnswer })

            alert("Gửi khảo sát thành công, xin cảm ơn")
            nav("/surveys")


        } catch (ex) {
            console.error(ex)
        }
    }


    if (user === null)
        return <Navigate to="/login" />

    return (
        <>
            <Header />
            <div style={styles.container}>
                <div style={styles.container_child}>

                    {surveyDetail === null ? <>
                        <Spinner animation="border" variant="primary" />
                    </> : <>
                        <h1 style={{ marginBottom: "40px" }}><Badge>{surveyDetail.title}</Badge></h1>
                        <Table bordered size="xl">
                            <tr style={{ height: "50px" }}>
                                <th style={{ paddingLeft: "100px" }}>Id</th>
                                <th>Câu hỏi khảo sát</th>
                                <th>Tốt</th>
                                <th>Không tốt</th>
                            </tr>

                            {listAnswer.length === 0 ? <>
                                <Spinner animation="border" variant="primary" />
                            </> : <>
                                {surveyDetail.questions.map((i, index) => <Fragment key={i.id}>
                                    <tr style={{ height: "70px" }}>
                                        <td style={{ paddingLeft: "100px" }}>{i.id}</td>
                                        <td>{i.content}</td>
                                        <td >
                                            <input
                                                type="radio"
                                                name={i.id} value={1}
                                                onChange={(e) => handleContentChange(e, index)} 
                                                />
                                        </td>
                                        <td style={{ paddingLeft: "40px" }}>
                                            <input
                                                type="radio"
                                                name={i.id}
                                                value={0}
                                                onChange={(e) => handleContentChange(e, index)}
                                                />
                                        </td>
                                    </tr>
                                </Fragment>)}
                            </>}

                        </Table>
                        <Button type="button" style={{ marginLeft: "20px" }} onClick={handleSubmit}>Submit</Button>
                    </>}

                </div>
            </div>

            <Footer />
        </>
    )
}
export default SurveyDetail