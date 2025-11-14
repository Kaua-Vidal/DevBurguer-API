import { Button } from '../Button';
import { Container } from './styles';
import { toast } from 'react-toastify'; //Para aparecer os PopUps
import { useEffect, useState} from 'react';
import {useCart} from '../../hooks/CartContext';
import {api} from '../../services/api';
import { useNavigate } from 'react-router-dom';
import {formatPrice} from '../../utils/formatPrice';
import { ApertureIcon } from '@phosphor-icons/react';
//Usa o {} quando não é exportado Default

export function CartResume() {
    const [finalPrice, setFinalPrice] = useState(0);
    const [deliveryTax] = useState(500); //5R$
    const navigate = useNavigate();

    const { cartProducts, clearCart } = useCart();


    //UseEffect é quando algo altera na tela, ele recarrega e traz valores atualizados
    useEffect(() => { 
        //Reduce serve para transformar todos os valores em um valor final
        //O 0 no final é o valor que vai iniciar
        const sumAllItems = cartProducts.reduce((acc, current) => {
            return current.price * current.quantity + acc;
        }, 0)

        setFinalPrice(sumAllItems)
    }, [cartProducts])


    //Usamos async quando vamos nos comunicar com a API
    const submitOrder = async () => {  
        const products = cartProducts.map( (product) => {
            return { id: product.id, quantity: product.quantity, price: product.price}
        })
        
        try {
            const {data} = await api.post('create-payment-intent', {products});
            navigate('/checkout', {
                //O state serve para mandar info de uma rota para outra
                state: data,
            })
        } catch (error) {
            toast.error('Erro, tente novamente!', {
                position: "top-right",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: false,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
        }
         
        /*
        //try {
        const { status } = 
        await api.post('/orders', {products}, {
        validateStatus: () => true,
        })
    

        if (status === 200 || status === 201) {
            
        setTimeout(() => {
            navigate('/')
        }, 2000);

        clearCart()
            toast.success('Pedido realizado com Sucesso')
        } else if (status === 400 || status === 409) {
            toast.error('Falha ao realizar o seu pedido')
        } else {
            throw new Error()
        }
        //} catch (error) {
        //toast.error('Falha no sistema! Tente novamente')
        }*/


    }

    return (
        <div>
        <Container>
            <div className="container-top">
                <h2 className='title'>Resumo do Pedido</h2>
                <p className='items'>Itens</p>
                <p className='items-price'>{formatPrice(finalPrice)}</p>
                <p className='delivery-tax'>Taxa de Entrega</p>
                <p className='delivery-tax-price'>{formatPrice(deliveryTax)}</p>
            </div>
            <div className="container-bottom">
                <p>Total</p>
                <p>{formatPrice(finalPrice + deliveryTax)}</p>
            </div>

        </Container>
        <Button onClick={submitOrder}>Finalizar Pedido</Button>
        </div>
    )
}