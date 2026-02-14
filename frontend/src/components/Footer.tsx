const Footer = () => {
    return (
        <footer style={{ padding: '40px 20px', background: 'var(--bg-secondary)', borderTop: '1px solid var(--glass-border)' }}>
            <div className="container" style={{
                maxWidth: '1200px',
                margin: '0 auto',
                textAlign: 'center',
                color: 'var(--text-secondary)'
            }}>
                <p>© 2026 HealthTrack. All rights reserved.</p>
                <p style={{ marginTop: '16px', fontSize: '0.9rem' }}>
                    Made with ❤️ by Burak Can Aksoy
                </p>
            </div>
        </footer>
    );
};

export default Footer;
