import Cart from '../../assets/cart.svg'
import { ContainerButton } from './styles'

export function CartButton({...props}) {


    return (
            //"Pegue todas as propriedades que alguém para <CartButton/>"
        <ContainerButton {...props}>
            <img src={Cart} alt='carrinh-de-compras'/>
            <h1>Adicionar</h1>
        </ContainerButton>
    )
}