import { Toaster } from "react-hot-toast"

export const Footer = () => {
    const styles = {
        txt: {
            color: "#fff",
            marginLeft: 10
        },
        footer_block: {
            height: "100px", backgroundColor: "#0d6efd"
        }
    }

    return (
        <>

            <div style={styles.footer_block}>
                
                <h4 style={styles.txt}>BK Apartment &copy; 2024</h4>
                <p style={styles.txt}>Thông tin liên hệ: 2151013009binh@ou.edu.vn</p>
                <p style={styles.txt}>Hotline: 0707.xxx.xxx</p>
            </div>
        </>
    )
}