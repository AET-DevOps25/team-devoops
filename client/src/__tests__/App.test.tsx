import { render, screen } from '@testing-library/react';
import App from '../App';

// Mock the required props
const mockProps = {
  toggleColorMode: jest.fn(),
  mode: 'light' as const,
};

describe('App', () => {
  it('renders the coming soon message', () => {
    render(<App {...mockProps} />);
    expect(screen.getByText('Coming Soon')).toBeInTheDocument();
  });

  it('renders the mensa image', () => {
    render(<App {...mockProps} />);
    const image = screen.getByAltText('Meet@Mensa');
    expect(image).toBeInTheDocument();
  });
});
