import { useLocation, useNavigate } from "react-router-dom"
import { useState } from "react";
import {
  PaymentElement,
  useStripe,
  useElements
} from "@stripe/react-stripe-js";
import '../styles.css'
import { useCart } from "../../../hooks/CartContext";
import { api } from "../../../services/api";
import { toast } from "react-toastify";



export default function CheckoutForm() {
  const navigate = useNavigate();
  const { cartProducts, clearCart } = useCart();
  const stripe = useStripe();
  const elements = useElements();
  const { state: { clientCherckerLink } } = useLocation()

  const [message, setMessage] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!stripe || !elements) {
      console.error('Stripe or elements fail, try again!')
      return;
    }

    setIsLoading(true);

    const { error, paymentIntent } = await stripe.confirmPayment({
      elements,
      redirect: 'if_required',
    });

    if (error) {

      setMessage(error.message);
      toast.error(error.message)
    } else if (paymentIntent && paymentIntent.status == 'succeeded') {
      try {
        const products = cartProducts.map((product) => {
          return { id: product.id, quantity: product.quantity, price: product.price }
        })

        const { status } =
          await api.post('/orders', { products }, {
            validateStatus: () => true,
          })

        if (status === 200 || status === 201) {
          setTimeout(() => {
            navigate('/complete'),
              clearCart();
          }, 2000);
          clearCart()
          toast.success('Pedido realizado com Sucesso')
        } else if (status === 400 || status === 409) {
          toast.error('Falha ao realizar o seu pedido')
          
        } else {
          throw new Error()
        }
      } catch (error) {
        toast.error('Falha no sistema! Tente novamente')
      }
      
    }

     else {
      toast.error('Falha no sistema! Tente novamente')
    }
    setIsLoading(false);
    
  }

    const paymentElementOptions = {
      layout: "accordion"
    }

    return (
      <div className="container">
        <form id="payment-form" onSubmit={handleSubmit}>

          <PaymentElement id="payment-element" options={paymentElementOptions} />
          <button disabled={isLoading || !stripe || !elements} id="submit" className="button">
            <span id="button-text">
              {isLoading ? <div className="spinner" id="spinner"></div> : "Pagar Agora"}
            </span>
          </button>
          {/* Show any error or success messages */}
          {message && <div id="payment-message">{message}</div>}
        </form>
      </div>
    );
}