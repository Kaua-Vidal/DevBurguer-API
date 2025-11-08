import {
  Container,
  HeaderLink,
  LinkContainer,
  Logout,
  Navigation,
  Options,
  Profile,
  Content,
} from './styles';
import { UserCircleIcon, ShoppingCartIcon } from '@phosphor-icons/react';
import { useNavigate, useResolvedPath } from 'react-router-dom';
import { useUser } from '../../hooks/UserContext';

export function Header() {
    const navigate = useNavigate()
    const {pathname} = useResolvedPath();
    const { logout, userInfo} = useUser();

    function logoutUser() {
        logout();
        navigate('/login')
    }
  return (
    <Container>
      <Content>
        <Navigation>
          <div>
            <HeaderLink to='/' $isActive={pathname === '/'}>Home</HeaderLink>
            <hr></hr>
            <HeaderLink to='/cardapio' $isActive={pathname === '/cardapio'}>
            Cardápio
            </HeaderLink>
          </div>
        </Navigation>
        <Options>
          <Profile>
            <UserCircleIcon color="#ffffff" size={24} />
            <div>
              <p>
                Olá, <span>{userInfo.name}</span>
              </p>
              <Logout onClick={logoutUser}>Sair</Logout>
            </div>
          </Profile>
          <LinkContainer>
            <ShoppingCartIcon color="#ffffff" size={24} />
            <HeaderLink to='/carrinho'>Carrinho</HeaderLink>
          </LinkContainer>
        </Options>
      </Content>
    </Container>
  );
}
