import styled from "styled-components";

export const ProductImage = styled.img`
    height: 80px;
    width: 80px;
    border-radius: 16px;
`
export const ButtonGroup = styled.div`
    display: flex;
    align-items: center;
    gap: 12px;

    button {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 30px;
        width: 30px;
        color: ${(props) => props.theme.white};
        border-radius: 4px;
        background: ${(props) => props.theme.gradients.redGradient};
        transition: all 300ms ease;
        border: none;
        box-shadow: 0px 5px 10px 2px rgba(0,0,0,0.4);

        &:hover {
            background: ${(props) => props.theme.gradients.secondRedGradient};
            transform: translateY(-2px);
            box-shadow: 0px 10px 10px 2px rgba(0,0,0,0.4);
        }

        &:active{
            transform: scale(1.05);
        }
    }
`
export const EmptyCart = styled.p`
    font-size: 20px;
    text-align: center;
    font-weight: bold;
`

export const ProductTotalPrice = styled.p`
    font-weight: bold;
`

export const TrashImage = styled.img`
    height: 20px;
    width: 20px;
    cursor: pointer;
`
