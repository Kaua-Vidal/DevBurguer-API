import { navLinks } from "./navLinks";
import Logo from '../../assets/logo.svg'
import { SignOutIcon } from "@phosphor-icons/react";
import { Container, NavLinkContainer, NavLink, Footer } from "./styles";
import { useUser } from "../../hooks/UserContext";
import { useResolvedPath } from "react-router-dom";

export function SideNavAdmin() {
    const {logout} = useUser();
    const {pathname} = useResolvedPath();

    return (
        <Container>
            <img src={Logo} alt="Hamburguer Logo DevBurguer" />
            <NavLinkContainer>
                {navLinks.map( (link) => (
                    <NavLink 
                    key={link.id}
                    to={link.path}
                    //O $ indica que será usado no styledComponents
                    $isActive={pathname === link.path}
                    >
                        {link.icon}
                        <span>
                        {link.label}
                        </span>
                    </NavLink>
                ))}
            </NavLinkContainer>
            <Footer>
                <NavLink to='/login' onClick={logout}>
                    <SignOutIcon/>
                    <span>Sair</span>
                </NavLink>
            </Footer>
        </Container>
    )
}