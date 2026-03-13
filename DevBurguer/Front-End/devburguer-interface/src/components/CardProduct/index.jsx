import PropTypes from "prop-types";
import { CardImage, Container } from "./styles";
import { CartButton } from "../CartButton";
import { useCart} from '../../hooks/CartContext';
import { toast } from "react-toastify";

export function CardProduct({ product }) {
    const { putProductInCart } = useCart();
    
    return (
    
    <Container>
      <CardImage src={product.url} alt={product.name}/>
      <div>
        <p>{product.name}</p>
        <strong>{product.currencyValue}</strong>
      </div>
      <CartButton onClick={ () => {putProductInCart(product)}}></CartButton>
    </Container>
  );
}


CardProduct.propTypes = {
    product: PropTypes.object,
}