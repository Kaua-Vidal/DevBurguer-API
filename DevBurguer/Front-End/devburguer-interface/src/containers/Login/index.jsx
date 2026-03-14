import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { api } from '../../services/api';
import { toast } from 'react-toastify';
import {
  Container,
  Form,
  InputContainer,
  LeftContainer,
  RightContainer,
  Title,
  Link,
  GoogleButton,
} from './styles';
import Logo from '../../assets/logo.png';
import { Button } from '../../components/Button';
import { useNavigate } from 'react-router-dom';
import { useUser } from '../../hooks/UserContext';
import { useState } from 'react';
import { jwtDecode } from 'jwt-decode';
import { GoogleLogin } from '@react-oauth/google';

export function Login() {
  const navigate = useNavigate();
  const { putUserData } = useUser()
  const [user, setUser] = useState(null);

  const schema = yup
    .object({
      email: yup
        .string()
        .email('Digite um e-mail válido')
        .required('O e-mail é obrigatório'),
      password: yup
        .string()
        .min(6, 'A senha deve ter pelo menos 6 caracteres')
        .required('Digite uma senha'),
    })
    .required();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(schema),
  });

  const onSubmit = async (data) => {
    const {
      data: userData
    } = await toast.promise(
      api.post('/sessions', {
        email: data.email,
        password: data.password,
      }),
      {
        pending: 'Verificando seus dados',
        success: {
          render() {
            setTimeout(() => {
              if(userData?.admin) {
                navigate('/admin/pedidos');
              } else {
                navigate('/');
              }
              
             
            }, 2000);
            return `Seja Bem-vindo(a)`;
          },
        },
        error: 'E-mail ou Senha incorretos',
      },
    );


    putUserData(userData);
  };

  const handleSucces = (credentialResponse) => {
    const userData = jwtDecode(credentialResponse.credential);
    setUser(userData);
    
  }

  const handleError = () => {
    toast.error("Não foi possível logar. Tente novamente!")
  }


  return (
    <Container>
      <LeftContainer>
        <img src={Logo} alt="logo-devburguer" />
      </LeftContainer>

      <RightContainer>
        <Title>
          Olá, seja bem vindo ao <span>Stack Burguer!</span>
          <br />
          Acesse com seu
          <span> Login e senha.</span>
        </Title>

        <Form onSubmit={handleSubmit(onSubmit)}>
          <InputContainer>
            <label htmlFor="">Email</label>
            <input type="email" {...register('email')} />
            <p>{errors?.email?.message}</p>
          </InputContainer>

          <InputContainer>
            <label htmlFor="">Senha</label>
            <input type="password" {...register('password')} />
            <p>{errors?.password?.message}</p>
          </InputContainer>
          <Button type="submit">Entrar</Button>
        </Form>

        <GoogleButton>
          <GoogleLogin
            onSuccess={handleSucces}
            onError={handleError}
            size='large'
            shape='rectangular'
            text='continue_with'
            width={350}/>
            
        </GoogleButton>

        <p>
          Não possui conta? <Link to="/cadastro">Clique aqui.</Link>
        </p>
      </RightContainer>
    </Container>
  );
}
