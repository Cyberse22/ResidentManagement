import { useContext, useRef, useState } from "react";
import { UserContext } from "../configs/Contexts";
import { Navigate, useNavigate } from "react-router-dom";
import { Alert, Button, Form, Spinner } from "react-bootstrap";
import { authApi, endpoints } from "../configs/APIs";

const UpdateUser = () => {

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

    const current_user = useContext(UserContext)
    const [user, setUser] = useState(current_user)
    const avatar = useRef()
    const nav = useNavigate()
    const [loading, setLoading] = useState(false)

    const change = (event, field) => {
        setUser(current => {
            return { ...current, [field]: event.target.value }
        })
    }

    const update_user = async (e) => {
        e.preventDefault()

        if (user.password !== user.confirm) {
            alert("Xác nhận mật khẩu sai !")
            return nav("/update")
        }

        if (avatar.current.files[0] === null || avatar.current.files[0] === undefined) {
            alert("Vui lòng chọn ảnh đại diện !")
            return nav("/update")
        }

        let form = new FormData()
        for (let k in user)
            if (k !== 'confirm' && k !== 'file')
                form.append(k, user[k]);
        form.append('file', avatar.current.files[0]);

        try {
            setLoading(true)
            let res = await authApi().post(endpoints['update-user'], form, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            if (res.status === 200)
                window.location.reload()
        } catch (ex) {
            console.error(ex);
        } finally {
            setLoading(false)
        }

    }

    if (current_user === null)
        return <Navigate to="/login" />


    return (
        <>
            <div style={styles.container_login}>
                <div style={{ alignItems: "center", alignContent: "center" }}>
                    <div style={styles.container_form_login}>
                        <h4 className="text-center  mt-3 mb-3">Vui lòng cập nhật thông tin</h4>
                        <Form onSubmit={update_user}>
                            <Form.Group className="mb-3" controlId="password">
                                <Form.Label>Mật khẩu</Form.Label>
                                <Form.Control onChange={e => change(e, "password")} type="password" placeholder="********" />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="confirm">
                                <Form.Label>Xác nhận mật khẩu</Form.Label>
                                <Form.Control onChange={e => change(e, "confirm")} value={user["confirm"]} type="password" placeholder="********" />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="confirm">
                                <Form.Label>Chọn ảnh đại diện</Form.Label>
                                <Form.Control ref={avatar} type="file" accept=".png,.jpg" placeholder="Ảnh đại diện" />
                            </Form.Group>

                            {loading === true ? <>
                                <div className="d-flex justify-content-center">
                                    <Spinner variant="primary" />
                                </div>
                            </> : <>
                                <div className="d-flex justify-content-center">
                                    <Button variant="primary" type="submit" className="mb-1 mt-1 ">Xác nhận</Button>
                                </div>
                            </>}

                        </Form>
                    </div>
                </div>
            </div>
        </>
    )


}


export default UpdateUser;