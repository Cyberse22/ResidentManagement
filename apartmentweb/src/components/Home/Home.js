import Slide from "./Slide";
import { Header } from "../Common/Header";
import { useContext } from "react";
import { UserContext } from "../../configs/Contexts";
import { Navigate } from "react-router-dom";
import { Footer } from "../Common/Footer";
import { Toaster } from "react-hot-toast";


const Home = () => {
    const user = useContext(UserContext)

    if (user === null)
        return <Navigate to="/login" />

    if (user.avatar === null || user.avatar === '') {
        return <Navigate to="/update" />
    }


    return (
        <>
            
            <Header />
            <Slide />
            <Footer/>

        </>
    );
}

export default Home;