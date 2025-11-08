import styled from 'styled-components';
import Background from '../../assets/background.svg'
import BannerHarmburguer from '../../assets/banner-hamburguer.svg'
import { Link } from 'react-router-dom';

export const Container = styled.div`
    width: 100%;
    min-height: 100vh;
    background-color: #f0f0f0;

    background: linear-gradient(
        rgba(255,255,255, 0.5),
        rgba(255,255,255,0.5)
    ), url('${Background}');
    height: 100%;
`

export const Banner = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    height: 480px;
    width: 100%;
    position: relative;

    background: url('${BannerHarmburguer}');
    background-position: center;
    background-color: #1f1f1f;
    background-size: cover;

    h1 {
        font-family: 'Road Rage', sans-serif;
        font-size: 80px;
        line-height: 65px;
        color: #fff;
        position: absolute;

        right: 20%;
        top: 30%;

        span {
            display: block;
            color: #fff;
            font-size: 20px;
        }
    }
`;
export const CategoryMenu = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 50px;
    margin-top: 30px;
    margin-right: 40px;
`;
export const ProductsContainer = styled.div`
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    padding: 40px;
    gap: 60px;
    justify-content: center;
    max-width: 1280;
    margin: 50px auto;
`;

export const CategoryButton = styled(Link) `
    transition: all 300ms ease;
    text-decoration: none;
    cursor: pointer;
    background: none;
    color: ${props => props.$isActiveCategory ? '#9758a6' : '#656565ff'};
    font-size: 24px;
    font-weight: 500;
    padding-bottom: 5px;
    line-height: 20px;
    border: none;
    border-bottom: ${ props => props.$isActiveCategory && '3px solid #9758a6'};
`;

export const HomeButton = styled.button `
    display: flex;
    align-items: center;
    position: relative;
    top: 0px;
    right: 15%;
    padding: 5px;
    background: none;
    border: none;
    color: #9758a6;
    font-weight: 500;
    font-size: 20px;
    transition: all 300ms ease;

    &::before {
        content: '«';
        margin-right: 3px;
    }

    &:hover {
        transform: scale(1.05);
    }
`
