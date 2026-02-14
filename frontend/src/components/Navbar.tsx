import { Link } from 'react-router-dom';
import { Activity, Menu, X } from 'lucide-react';
import { useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';

const Navbar = () => {
    const [isOpen, setIsOpen] = useState(false);

    return (
        <nav className="fixed w-full z-50 glassnav" style={{
            position: 'fixed',
            top: 0,
            width: '100%',
            zIndex: 100,
            background: 'rgba(15, 23, 42, 0.8)',
            backdropFilter: 'blur(10px)',
            borderBottom: '1px solid var(--glass-border)'
        }}>
            <div className="container" style={{
                maxWidth: '1200px',
                margin: '0 auto',
                padding: '1rem 2rem',
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center'
            }}>
                <Link to="/" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', fontWeight: 700, fontSize: '1.5rem', color: 'var(--text-primary)' }}>
                    <Activity size={32} color="var(--color-primary)" />
                    <span>Health<span style={{ color: 'var(--color-secondary)' }}>Track</span></span>
                </Link>

                {/* Desktop Menu */}
                <div style={{ display: 'flex', gap: '2rem', alignItems: 'center' }} className="hidden-mobile">
                    {['Features', 'About', 'Contact'].map((item) => (
                        <a key={item} href={`#${item.toLowerCase()}`} style={{ color: 'var(--text-secondary)', fontWeight: 500 }}>
                            {item}
                        </a>
                    ))}
                    <Link to="/login" style={{ color: 'var(--text-primary)', fontWeight: 500 }}>
                        Login
                    </Link>
                    <Link to="/signup" style={{
                        background: 'linear-gradient(135deg, var(--color-primary), var(--color-secondary))',
                        padding: '0.5rem 1.5rem',
                        borderRadius: '2rem',
                        fontWeight: 600
                    }}>
                        Get Started
                    </Link>
                </div>

                {/* Mobile Toggle */}
                <div className="md:hidden" style={{ display: 'none' }}>
                    {/* Note: I'm using inline styles for now but responsive needs media queries. 
              Since I'm sticking to Vanilla CSS, I should move styles to CSS or styled-components.
              But for now, I'll add a <style> block or just use index.css classes.
          */}
                    <button onClick={() => setIsOpen(!isOpen)}>
                        {isOpen ? <X color="white" /> : <Menu color="white" />}
                    </button>
                </div>
            </div>
        </nav>
    );
};

export default Navbar;
