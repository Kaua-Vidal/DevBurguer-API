import styled from 'styled-components';

export const ContainerButton = styled.button`
    width: 100%;
    height: 52px;
    border-radius: 5px;
    background-color: ${(props) => props.theme.purple };
    font-family: 'Road Rage', sans-serif;
    font-size: 30px;
    color: ${(props) => props.theme.white};
    border: none;
    transition: all 300ms ease;

    &:hover {
        transform: scale(1.05);
        background-color: ${(props) => props.theme.secondDarkPurple};
        background-image: url("data:image/svg+xml,%3csvg width='100%25' height='100%25' xmlns='http://www.w3.org/2000/svg'%3e%3crect width='100%25' height='100%25' fill='none' rx='5' ry='5' stroke='white' stroke-width='3' stroke-dasharray='9' stroke-dashoffset='34' stroke-linecap='square'/%3e%3c/svg%3e");
        border-radius: 5px;
    }

    &:active {
        background-color: #9948abff;
        color:#000;
        border:none;
}
`;
