import styled from 'styled-components';

export const ContainerButton = styled.button`
    width: 100%;
    height: 52px;
    border-radius: 5px;
    background: ${(props) => props.theme.gradients.redGradient};
    font-family: 'Road Rage', sans-serif;
    font-size: 30px;
    color: ${(props) => props.theme.white};
    border: none;
    transition: all 300ms ease;
    box-shadow: 0px 4px 10px rgba(0,0,0,0.3);
    box-shadow: inset 0 2px 3px rgba(255,255,255,0.4), 
                0 4px 10px rgba(0,0,0,0.3);
    transition: 0.2s;
    position: relative;
    overflow: hidden;

    &:hover {
        transform: translateY(-2px);
        background: ${(props) => props.theme.gradients.secondRedGradient};
        border-radius: 5px;
        box-shadow:
        inset 0 2px 5px rgba(255,255,255,0.5),
        0 8px 18px rgba(0,0,0,0.4);
    }

    &:active {
        background: ${(props) => props.theme.gradients.redGradient};
        color:#000;
        border:none;
    }

    &::before {
        content: "";
        position: absolute;
        top: 0;
        left: -100%;
        width: 100%;
        height: 100%;

        background: linear-gradient(
            120deg,
            transparent,
            rgba(255,255,255,0.5),
            transparent
        );

    transition: 0.5s;

    
    }
    
    &:hover::before {
    left: 100%;
    }
`;
