import { Link } from "react-router-dom";
import styled from "styled-components";

export const Container = styled.nav`
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100vh;
    align-items: center;
    background-color: ${props => props.theme.black};

    img {
        width: 70%;
        margin: 40px 0;

    }

`
export const NavLinkContainer = styled.div`
    display: flex;
    flex-direction: column;
    width: 100%;
    padding: 0 15px;
    gap: 10px;
`
export const Footer = styled.footer`
    display: flex;
    flex-direction: column;
    width: 100%;
    margin-top: auto;
    padding: 0 15px;
    gap: 10px;  
`
export const NavLink = styled(Link)`
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 12px 20px;
    text-decoration: none;
    color: ${props => props.theme.white};
    background: ${props => props.$isActive ? props.theme.gradients.redGradient : 'transparent'};
    border-radius: 5px;
    box-shadow: ${(props) => props.$isActive ? '5px 5px 15px 5px rgba(0,0,0,0.3)' : 'none'};
    transition: all 300ms ease;


    &:hover {
        background: ${props => props.theme.gradients.redGradient};
    }
`