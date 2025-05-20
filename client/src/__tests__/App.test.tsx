import { render, screen } from '@testing-library/react';
import App from '../App';

describe('App', () => {
  it('renders the coming soon message', () => {
    render(<App />);
    expect(screen.getByText('Coming Soon')).toBeInTheDocument();
  });

  it('renders the mensa image', () => {
    render(<App />);
    const image = screen.getByAltText('Meet@Mensa');
    expect(image).toBeInTheDocument();
  });
});
