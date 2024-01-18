import {
  createBrowserRouter,
  RouterProvider,
  // createRoutesFromElements,
} from 'react-router-dom';
import HomePage from './pages/Home';
import ProductsPage from './pages/Products';
import RootLayout from './pages/Root';
import ErrorPage from './pages/ErrorPage';
import ProudctDetailPage from './pages/ProductDetail';

// https://example.com/products

// const routeDefinitions = createRoutesFromElements(
//   <Route>
//     <Route path="/" element={<HomePage />} />
//     <Route path="/products" element={<ProductsPage />} />
//   </Route>
// );

// const router = createBrowserRouter(routeDefinitions);

const router = createBrowserRouter([
  {
    path: '/',
    element: <RootLayout />,
    errorElement: <ErrorPage />,
    children: [
      { index: true, element: <HomePage /> }, // path: '',
      { path: 'products', element: <ProductsPage /> },
      { path: 'products/:productId', element: <ProudctDetailPage /> },
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
