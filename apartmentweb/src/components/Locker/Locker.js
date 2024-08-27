import { Alert, Table } from "react-bootstrap"
import { Footer } from "../Common/Footer"
import { Header } from "../Common/Header"
import { Fragment, useContext, useEffect, useState } from "react"
import { UserContext } from "../../configs/Contexts"
import { authApi, endpoints } from "../../configs/APIs"
import { Navigate } from "react-router-dom"

const Locker = () => {

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
    const [items, setItems] = useState([])

    const loadItems = async ()=>{
        try{
            let res = await authApi().get(endpoints['items'](user.id))
            setItems(res.data)
        }catch(ex){
            console.error(ex)
        }
    }

    useEffect(()=>{
        loadItems()
    },[user])

    if (user === null)
        return <Navigate to="/login" />

    return (
        <>
            <Header />
            <div style={styles.container}>

                <div style={styles.container_child} >
                    <Alert variant="danger">&#10071; Danh sách hàng trong tủ đồ điện tử của cư dân, vui lòng liên hệ ban quản lý để nhận hàng</Alert>
                    <Table bordered size="xl">
                        <tr style={{height:"50px"}}>
                            <th style={{paddingLeft:"100px"}}>Id</th>
                            <th>Tên hàng</th>
                            <th>Ngày giao</th>
                        </tr>
                        {items.map(i => <Fragment key={i.id}>
                            <tr style={{height:"50px"}}>
                                <td style={{paddingLeft:"100px"}}>{i.id}</td>
                                <td>{i.description}</td>
                                <td>{i.createdDate}</td>
                            </tr>
                        </Fragment>)}
                    </Table>
                </div>
            </div>


            <Footer />
        </>
    )
}
export default Locker