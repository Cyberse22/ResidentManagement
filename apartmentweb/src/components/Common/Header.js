import { useContext, useEffect, useState } from "react"
import { Badge, Button, Image, Nav, NavDropdown, Navbar, OverlayTrigger, Tooltip } from "react-bootstrap"
import { Link } from "react-router-dom"
import { DispatchContext, UserContext } from "../../configs/Contexts"
import APIs, { endpoints } from "../../configs/APIs"

export const Header = () => {
    const dispatch = useContext(DispatchContext)
    const user = useContext(UserContext)

    const [surveys, setSurveys] = useState([])

    const loadSurveys = async () => {
        try {
            const res = await APIs.get(endpoints['surveys'])
            setSurveys(res.data)
        } catch (ex) {
            console.error(ex)
        }
    }
    useEffect(() => {

        loadSurveys()

    }, [])

    const renderTooltip = (props) => (
        <Tooltip id="button-tooltip" {...props}>
            {surveys.length} khảo sát cần thực hiện
        </Tooltip>
    );

    return (
        <Navbar bg="light" data-bs-theme="light" expand="lg" className="bg-body-tertiary">

            <Navbar.Brand>
                <Image src="https://res.cloudinary.com/dwdvnztnn/image/upload/v1716542240/2_k3qyxl.png" roundedCircle width="100" height="100" />
            </Navbar.Brand>
            <Link to="/" className="nav-link">BK Apartment</Link>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="me-auto">
                    <NavDropdown title="Tiện ích" id="basic-nav-dropdown">
                        <Link to="/invoices" className="nav-link">Thanh toán hóa đơn</Link>
                        <Link to="/visitor_card" className="nav-link">Đăng ký thẻ xe</Link>
                        <Link to="/locker" className="nav-link">Tủ đồ của tôi</Link>
                        <Link to="/feedbacks" className="nav-link">Phản hồi</Link>
                    </NavDropdown>

                    <OverlayTrigger
                        placement="bottom"
                        delay={{ show: 250, hide: 400 }}
                        overlay={renderTooltip}
                    >
                        <Link to="/surveys" className="nav-link">Khảo sát <Badge bg="danger">{surveys.length}</Badge></Link>
                    </OverlayTrigger>

                    {user && <>
                        <Link to="/" className="nav-link">Cư dân: {user.firstName} {user.lastName}</Link>
                        <Button className="ms-2" variant="primary" onClick={() => dispatch({ "type": "logout" })}>Đăng xuất</Button>
                    </>
                    }
                </Nav>
            </Navbar.Collapse>




        </Navbar>
    )
}