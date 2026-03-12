import styled from "styled-components";

export const ContainerButton = styled.button`
    display: flex;
    align-items: center;
    justify-content: space-around;
    background: ${(props) => props.theme.gradients.redGradient};
    width: 100%;
    height: 52px;
    border: 0;
    border-radius: 5px;
    font-size: 30px;
    color: ${(props) => props.theme.white};
    transition: all 300ms ease;
    box-shadow: 0px 4px 10px rgba(0,0,0,0.3);
    box-shadow: inset 0 2px 3px rgba(255,255,255,0.4), 
                0 4px 10px rgba(0,0,0,0.3);


    &:hover {
        background: ${(props) => props.theme.gradients.secondRedGradient};
        transform: translateY(-2px);
        box-shadow:
        inset 0 2px 5px rgba(255,255,255,0.5),
        0 8px 18px rgba(0,0,0,0.4);
    }

    img {
        margin-left: 20px;
    }

    h1 {
        color: #FFF;
        font-family: Poppins;
        font-size: 22px;
        font-style: normal;
        font-weight: 700;
        margin-right: 30px;
    }

`