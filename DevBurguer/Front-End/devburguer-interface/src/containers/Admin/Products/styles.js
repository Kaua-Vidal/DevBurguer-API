import styled from "styled-components";

export const Container = styled.div``
export const EditButton = styled.button`
    border: none;
    background-color: ${props => props.theme.darkWhite};
    height: 32px;
    width: 32px;
    border-radius: 8px;
    margin: 0 auto;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 300ms ease;

    svg {
        height: 18px;
        width: 18px;
    }

    &:hover {
        background: ${props => props.theme.gradients.redGradient};
        box-shadow: 1px 5px 15px 2px rgba(0,0,0,0.3);
        transform: translateY(-2px);

        svg {
            fill: ${props => props.theme.white};
        }
    }

    &:active {
        transform: scale(1.05);
    }
`
export const ProductImage = styled.img`
    max-width: 80px;
    padding: 12px;
    border-radius: 16px;
`