
import { Carousel} from "react-bootstrap";

const Slide = () => {
    return (
        <Carousel >
        <Carousel.Item interval={1000}>
            <img className="d-block w-100"
                src="https://ttcland.vn/pictures/catalog/tin-tuc/tin-thang-10/100-CHGS.png"
                alt="firstSlide"
                style={{ height: '90vh' }} />


        </Carousel.Item>
        <Carousel.Item interval={1000}>
            <img className="d-block w-100"
                src="https://ttcland.vn/pictures/catalog/ttcl---du-an/03---selavia/C02SelaviaScale3k.jpg"
                alt="firstSlide"
                style={{ height: '90vh' }} />

        </Carousel.Item>
        <Carousel.Item interval={1000}>
            <img className="d-block w-100"
                src="https://ttcland.vn/pictures/catalog/panomax-river-villa/05-Hnh-nh-nhn-vo-Block-J--thy-c-khng-gian-tin-ch-bn-di-facade-v-phn-rch-b-bm-ban-ngy-2.jpg"
                alt="firstSlide"
                style={{ height: '90vh' }} />

        </Carousel.Item>
        <Carousel.Item interval={1000}>
            <img className="d-block w-100"
                src="https://ttcland.vn/pictures/catalog/panomax-river-villa/04-Hnh-nh-nhn-mt-ngi-thy-view-b-sng.jpg"
                alt="firstSlide"
                style={{ height: '90vh' }} />

        </Carousel.Item>
    </Carousel>
    )
}
export default Slide;