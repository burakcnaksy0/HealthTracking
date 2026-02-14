import { useState } from 'react';
import { motion } from 'framer-motion';
import { Link, useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';
import api from '../api/axiosClient';

const Register = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        fullName: '',
        username: '',
        email: '',
        password: '',
        age: '',
        gender: 'MALE',
        weight: '',
        height: '',
        goal: 'MAINTAIN_WEIGHT',
        activityLevel: 'SEDENTARY'
    });

    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        setError('');

        try {
            // Backend expects numbers for age, weight, height
            const payload = {
                ...formData,
                age: parseInt(formData.age),
                weight: parseFloat(formData.weight),
                height: parseFloat(formData.height)
            };

            await api.post('/auth/register', payload);
            navigate('/login');
        } catch (err: any) {
            console.error(err);
            setError(err.response?.data?.message || 'Registration failed. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="flex-center" style={{ minHeight: '100vh', flexDirection: 'column', paddingTop: '100px', paddingBottom: '40px' }}>
            <Navbar />
            <motion.div
                initial={{ opacity: 0, scale: 0.9 }}
                animate={{ opacity: 1, scale: 1 }}
                style={{
                    padding: '40px',
                    width: '100%',
                    maxWidth: '600px',
                    background: 'var(--glass-bg)',
                    backdropFilter: 'blur(20px)',
                    borderRadius: '24px',
                    border: '1px solid var(--glass-border)',
                    boxShadow: '0 8px 32px 0 rgba(0, 0, 0, 0.37)'
                }}
            >
                <h2 className="gradient-text" style={{ textAlign: 'center', marginBottom: '24px' }}>Start Your Journey</h2>

                {error && (
                    <div style={{ color: '#ef4444', background: 'rgba(239, 68, 68, 0.1)', padding: '10px', borderRadius: '8px', marginBottom: '20px', textAlign: 'center' }}>
                        {error}
                    </div>
                )}

                <form onSubmit={handleSubmit} style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' }}>

                    {/* Full Width Fields */}
                    <div style={{ gridColumn: 'span 2' }}>
                        <label className="label">Full Name</label>
                        <input name="fullName" type="text" className="input" required onChange={handleChange} />
                    </div>

                    <div style={{ gridColumn: 'span 2' }}>
                        <label className="label">Email</label>
                        <input name="email" type="email" className="input" required onChange={handleChange} />
                    </div>

                    <div style={{ gridColumn: 'span 2' }}>
                        <label className="label">Username</label>
                        <input name="username" type="text" className="input" required onChange={handleChange} />
                    </div>

                    <div style={{ gridColumn: 'span 2' }}>
                        <label className="label">Password</label>
                        <input name="password" type="password" className="input" required minLength={8} onChange={handleChange} />
                    </div>

                    {/* Split Fields */}
                    <div>
                        <label className="label">Age</label>
                        <input name="age" type="number" className="input" required min="18" onChange={handleChange} />
                    </div>

                    <div>
                        <label className="label">Gender</label>
                        <select name="gender" className="input" onChange={handleChange}>
                            <option value="MALE" style={{ color: 'black' }}>Male</option>
                            <option value="FEMALE" style={{ color: 'black' }}>Female</option>
                        </select>
                    </div>

                    <div>
                        <label className="label">Weight (kg)</label>
                        <input name="weight" type="number" step="0.1" className="input" required min="30" onChange={handleChange} />
                    </div>

                    <div>
                        <label className="label">Height (cm)</label>
                        <input name="height" type="number" step="0.1" className="input" required min="100" onChange={handleChange} />
                    </div>

                    <div style={{ gridColumn: 'span 2' }}>
                        <label className="label">Goal</label>
                        <select name="goal" className="input" onChange={handleChange}>
                            <option value="WEIGHT_LOSS" style={{ color: 'black' }}>Weight Loss</option>
                            <option value="MAINTAIN_WEIGHT" style={{ color: 'black' }}>Maintenance</option>
                            <option value="WEIGHT_GAIN" style={{ color: 'black' }}>Muscle Gain</option>
                        </select>
                    </div>

                    <div style={{ gridColumn: 'span 2' }}>
                        <label className="label">Activity Level</label>
                        <select name="activityLevel" className="input" onChange={handleChange}>
                            <option value="SEDENTARY" style={{ color: 'black' }}>Sedentary (Office job)</option>
                            <option value="LIGHTLY_ACTIVE" style={{ color: 'black' }}>Lightly Active (1-3 days/week)</option>
                            <option value="MODERATELY_ACTIVE" style={{ color: 'black' }}>Moderately Active (3-5 days/week)</option>
                            <option value="VERY_ACTIVE" style={{ color: 'black' }}>Very Active (6-7 days/week)</option>
                            <option value="SUPER_ACTIVE" style={{ color: 'black' }}>Extra Active (Physical job)</option>
                        </select>
                    </div>

                    <button
                        type="submit"
                        disabled={loading}
                        style={{
                            gridColumn: 'span 2',
                            marginTop: '10px',
                            padding: '12px',
                            borderRadius: '12px',
                            background: 'linear-gradient(135deg, var(--color-primary), var(--color-secondary))',
                            color: 'white',
                            fontWeight: 600,
                            opacity: loading ? 0.7 : 1,
                            cursor: loading ? 'not-allowed' : 'pointer'
                        }}
                    >
                        {loading ? 'Creating Account...' : 'Sign Up'}
                    </button>
                </form>

                <div style={{ marginTop: '20px', textAlign: 'center', fontSize: '0.9rem', color: 'var(--text-secondary)' }}>
                    Already have an account? <Link to="/login" style={{ color: 'var(--color-accent)' }}>Log in</Link>
                </div>
            </motion.div>
        </div>
    );
};

export default Register;
