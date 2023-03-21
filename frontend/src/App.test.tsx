import React from 'react';
import { render, screen } from '@testing-library/react';
import RootLayout from './routes/RootLayout';

test('renders learn react link', () => {
  render(<RootLayout />);
  const linkElement = screen.getByText(/learn react/i);
  expect(linkElement).toBeInTheDocument();
});
