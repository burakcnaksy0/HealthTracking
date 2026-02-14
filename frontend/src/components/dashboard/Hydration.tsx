import { useState, useEffect } from 'react';
import api from '../../api/axiosClient';
import { motion } from 'framer-motion';
import { Droplets, Plus } from 'lucide-react';

const Hydration = () => {
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState('');
    const [sessionTotal, setSessionTotal] = useState(0);

    const fetchTodayTotal = async () => {
        try {
            const res = await api.get('/water/today');
            if (res.data.status === 'success') {
                setSessionTotal(res.data.data);
            }
        } catch (err) {
            console.error(err);
        }
    };


    useEffect(() => {
        fetchTodayTotal();
    }, []);

    const handleAddWater = async (amount: number) => {
        setLoading(true);
        try {
            const res = await api.post('/water/intake', { amount });
            if (res.data.status === 'success') {
                setSessionTotal(prev => prev + amount);
                setMessage(`Added ${amount}ml!`);
            }
        } catch (err) {
            console.error(err);
            setMessage('Failed to log water.');
        } finally {
            setLoading(false);
            setTimeout(() => setMessage(''), 2000);
        }
    };

    return (
        <div style={{ padding: '24px', textAlign: 'center' }}>
            <h2 className="gradient-text" style={{ fontSize: '2rem', marginBottom: '40px' }}>Hydration Tracker</h2>

            <div className="flex-center" style={{ gap: '40px', flexWrap: 'wrap' }}>
                <motion.div
                    whileHover={{ scale: 1.05 }}
                    className="glass"
                    style={{
                        padding: '40px',
                        borderRadius: '24px',
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                        gap: '20px',
                        width: '300px'
                    }}
                >
                    <Droplets size={64} color="var(--color-accent)" />
                    <h3 style={{ fontSize: '2rem' }}>{sessionTotal} ml</h3>
                    <p style={{ color: 'var(--text-secondary)' }}>Logged this session</p>
                </motion.div>

                <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
                    <h3 style={{ marginBottom: '16px' }}>Quick Add</h3>
                    {[250, 500, 750].map((amount) => (
                        <button
                            key={amount}
                            onClick={() => handleAddWater(amount)}
                            disabled={loading}
                            style={{
                                display: 'flex',
                                alignItems: 'center',
                                gap: '12px',
                                padding: '16px 32px',
                                background: 'rgba(255, 255, 255, 0.05)',
                                border: '1px solid var(--glass-border)',
                                borderRadius: '12px',
                                fontSize: '1.2rem',
                                color: 'var(--text-primary)',
                                cursor: loading ? 'wait' : 'pointer'
                            }}
                        >
                            <Plus size={20} /> {amount} ml
                        </button>
                    ))}
                    {message && <p style={{ marginTop: '10px', color: 'var(--color-accent)' }}>{message}</p>}
                </div>
            </div>
        </div>
    );
};

export default Hydration;
