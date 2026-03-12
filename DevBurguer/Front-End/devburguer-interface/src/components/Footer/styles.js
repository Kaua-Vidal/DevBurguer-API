import styled from "styled-components";
import Fundo from '../../assets/fundo.jpg';

export const Container = styled.div `
    height: 50px;
    background: url('${Fundo}');
    width: 100vw;
    display: flex;
    align-items: center;
    justify-content: center;

    p {
        color: ${(props) => props.theme.white};
        font-size: 14px;
        font-weight: lighter;
    }
`;