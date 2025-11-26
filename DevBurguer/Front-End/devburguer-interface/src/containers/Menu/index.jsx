import { useEffect, useState } from 'react';
import {
  Banner,
  CategoryButton,
  CategoryMenu,
  Container,
  HomeButton,
  ProductsContainer,
} from './styles';
import { api } from '../../services/api';
import { formatPrice } from '../../utils/formatPrice';
import { CardProduct } from '../../components/CardProduct';
import { useLocation, useNavigate } from 'react-router-dom';

export function Menu() {
  const [categories, setCategories] = useState([]);
  const [products, setProducts] = useState([]);
  const [filteredProducts, setFilteredProducts] = useState([]);

  const navigate = useNavigate();

  const { search } = useLocation();
  const queryParams = new URLSearchParams(search);

  const [activeCategory, setActiveCategory] = useState(() => {
    const categoryId = +queryParams.get('categoria');

    if (categoryId) {
      return categoryId;
    }
    return 0
  });
  console.log(queryParams);

  useEffect(() => {
    async function loadCategories() {
      const { data } = await api.get('/categories');

      const newCategories = [{ id: 0, name: 'Todas' }, ...data];

      setCategories(newCategories);
    }

    async function loadProducts() {
      const { data } = await api.get('/products');

      const newProducts = data.map((product) => ({
        currencyValue: formatPrice(product.price),
        ...product,
      }));
      setProducts(newProducts);
    }

    loadCategories();
    loadProducts();
  }, []);

  useEffect(() => {
  if (products.length === 0) return;

  if (activeCategory === 0) setFilteredProducts(products);
  else setFilteredProducts(products.filter(p => p.category_id === activeCategory));
}, [products, activeCategory]);

  return (
    <Container>
      <Banner>
        <h1>
          O MELHOR
          <br />
          HAMBURGUER
          <br />
          ESTÁ AQUI
          <span>Esse cardápio está irresistível!</span>
        </h1>
      </Banner>
      <CategoryMenu>
        <HomeButton onClick={() => navigate('/')}>
          Voltar
        </HomeButton>
        {categories.map((category) => (
          <CategoryButton
            key={category.id}
            to={`cardapio?categoria=${category.id}`}
            $isActiveCategory={category.id === activeCategory}
            onClick={() => {
              setActiveCategory(category.id)
            }}
            
          >
            {category.name}
            
          </CategoryButton>
        ))}
      </CategoryMenu>

      <ProductsContainer>
        {filteredProducts.map((product) => (
          <CardProduct product={product} key={product.id} />
        ))}
      </ProductsContainer>

      
    </Container>
  );
}
