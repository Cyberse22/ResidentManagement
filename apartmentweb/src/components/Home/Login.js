import { useContext, useState } from "react"
import { Button, Form, Spinner } from "react-bootstrap"
import { Navigate, useNavigate } from "react-router-dom";
import { DispatchContext, UserContext } from "../../configs/Contexts";
import APIs, { authApi, endpoints } from "../../configs/APIs";
import cookie from "react-cookies";
import { generateToken } from "../Firebase/firebase";


const Login = () => {
    const styles = {
        container_login: {
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            height: "100vh",
            backgroundImage: "url('https://ttcland.vn/pictures/catalog/panomax-river-villa/04-Hnh-nh-nhn-mt-ngi-thy-view-b-sng.jpg')",
        },
        container_form_login: {
            width: "350px",
            justifyContent: "center",
            alignItems: "center",
            backgroundColor: "#f8f9fa",
            border: "1px solid #D0D5D9",
            borderRadius: 14,
            padding: 10
        },

    }
    const [user, setUser] = useState({})
    const dispatch = useContext(DispatchContext)
    const currentUser = useContext(UserContext)
    const nav = useNavigate();
    const [fcmToken, setFcmToken] = useState("")
    const [loading, setLoading] = useState(false)


    const change = (event, field) => {
        setUser(current => {
            return { ...current, [field]: event.target.value }
        })
    }

    const getToken = async (username) => {
        const token = await generateToken()

        try {
            if (token !== "" && token !== null) {
                const res = await authApi().post(endpoints['update-token'], {
                    "username": username,
                    "token": token
                })
            }
        } catch (ex) {
            console.log("Lỗi update notification token khi gọi api update token")
        }
    }

    const login = async (e) => {
        e.preventDefault()

        try {
            setLoading(true)
            let res = await APIs.post(endpoints['login'], { ...user });
            cookie.save("token", res.data);

            setTimeout(async () => {
                let u = await authApi().get(endpoints['current-user'])
                console.info("56", u.data)
                if (u.data.active === 0) {
                    alert("Tài khoảng của bạn đã bị khóa")
                    nav("/login")
                } else {
                    dispatch({
                        "type": "login",
                        "payload": u.data
                    })
                }

                await getToken(u.data.username)
            }, 100)

            nav("/")


        } catch (ex) {
            alert("Tên đăng nhập hoặc mật khẩu sai")
            console.error(ex)
        } finally {
            setLoading(false)
        }
    }

    if (currentUser !== null)
        return <Navigate to="/" />


    return (
        <>
            <div style={styles.container_login}>
                <div style={{ alignItems: "center", alignContent: "center" }}>
                    <div style={styles.container_form_login}>
                        <h4 className="text-center  mt-3 mb-3">Vui lòng đăng nhập</h4>
                        <Form onSubmit={login}>
                            <Form.Group className="mb-3" controlId="username">
                                <Form.Label>Tên đăng nhập</Form.Label>
                                <Form.Control onChange={e => change(e, "username")} value={user["username"]} type="text" placeholder="username" />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="password">
                                <Form.Label>Mật khẩu</Form.Label>
                                <Form.Control onChange={e => change(e, "password")} value={user["password"]} type="password" placeholder="password" />
                            </Form.Group>


                            {loading === true ? <>
                                <div className="d-flex justify-content-center">
                                    <Spinner variant="primary" />
                                </div>

                            </> : <>
                                <div className="d-flex justify-content-center">
                                    <Button variant="primary" type="submit" className="mb-1 mt-1 ">Đăng nhập</Button>
                                </div>
                            </>}
                        </Form>
                    </div>
                </div>
            </div>
        </>
    )
}
export default Login