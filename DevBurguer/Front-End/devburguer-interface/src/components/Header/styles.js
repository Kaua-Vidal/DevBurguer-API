import { Link } from "react-router-dom";
import styled from "styled-components";
import Fundo from '../../assets/fundo.jpg';

export const Container = styled.div`
    background: url('${Fundo}');
    width: 100%;
    height: 72px;
    padding: 0 56px;
`;

export const Content = styled.div `
    display: flex;
    align-items: center;
    justify-content: space-between;

    width: 100%;
    max-width: 1280px;
    margin: 0 auto;
`
export const Navigation = styled.nav`
    display: flex;
    align-items: center;
    justify-content: center;
    height: 72px;
    margin-left: 40px;

    div {
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 20px;

        hr{
            height: 24px;
            border: 1px solid #655e5e
        }
    }

    img {
        position: absolute;
        left: 20px;
        margin-top: 40px;
        transform: rotate(-10deg);
        width: 120px;
        height: 120px;
        z-index: 999;
    }
`;

export const HeaderLink = styled(Link)`
    color: ${props => props.$isActive 
    ? (props) => props.theme.primaryRed 
    : (props) => props.theme.white};
    border-bottom: ${props => props.$isActive ? `1px solid ${(props) => props.theme.primaryRed}`  : 'none'};
    padding-bottom: 5px;
    text-decoration: none;
    font-size: 14px;
    transition: color 200ms;

    &:hover {
        color: ${(props) => props.theme.primaryRed }; 
    }
`;
export const Options = styled.div`
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 48px;
`;
export const Profile = styled.div`
    display: flex;
    align-items: center;
    gap: 12px;
    font-size: 14px;

    p {
        color: ${(props) => props.theme.white};
        line-height: 90%;
        font-weight: 300;

        span {
            font-weight: 700;
            color: ${(props) => props.theme.primaryRed };
        }

    }
`;
export const LinkContainer = styled(Link)`
    position: relative;
    display: flex;
    align-items: center;
    gap: 10px;
    text-decoration: none;
`;


export const Logout = styled.button`
    color: ${(props) => props.theme.red};
    text-decoration: none;
    font-weight: 700;
    background-color: transparent;
    border: none;
`;



export const InfoCart = styled.a`
    position: absolute;
    top: -7px;
    left: 14px;

    background-color: ${(props) => props.theme.primaryRed};
    width: 20px;
    height: 20px;
    border-radius: 50%;

    display: flex;
    align-items: center;
    justify-content: center;

    color: ${(props) => props.theme.white};

`