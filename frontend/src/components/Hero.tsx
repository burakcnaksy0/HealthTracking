import { motion } from 'framer-motion';
import { ArrowRight, Apple, Activity, Droplets } from 'lucide-react';

const Hero = () => {
    return (
        <section style={{
            padding: '160px 20px 100px',
            position: 'relative',
            overflow: 'hidden',
            minHeight: '90vh',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center'
        }}>
            {/* Background Glows */}
            <div style={{
                position: 'absolute',
                top: '20%',
                left: '20%',
                width: '400px',
                height: '400px',
                background: 'var(--color-primary)',
                filter: 'blur(150px)',
                opacity: 0.2,
                borderRadius: '50%',
                zIndex: -1
            }} />
            <div style={{
                position: 'absolute',
                bottom: '20%',
                right: '20%',
                width: '300px',
                height: '300px',
                background: 'var(--color-accent)',
                filter: 'blur(150px)',
                opacity: 0.2,
                borderRadius: '50%',
                zIndex: -1
            }} />

            <div className="container" style={{ textAlign: 'center', maxWidth: '800px', zIndex: 1 }}>
                <motion.div
                    initial={{ opacity: 0, y: 20 }}
                    animate={{ opacity: 1, y: 0 }}
                    transition={{ duration: 0.8 }}
                >
                    <span style={{
                        background: 'rgba(139, 92, 246, 0.1)',
                        color: 'var(--color-primary)',
                        padding: '8px 16px',
                        borderRadius: '20px',
                        fontSize: '0.9rem',
                        fontWeight: 600,
                        display: 'inline-block',
                        marginBottom: '24px',
                        border: '1px solid rgba(139, 92, 246, 0.2)'
                    }}>
                        Advanced Health Intelligence
                    </span>

                    <h1 style={{
                        fontSize: 'clamp(2.5rem, 5vw, 4.5rem)',
                        fontWeight: 800,
                        lineHeight: 1.1,
                        marginBottom: '24px',
                        letterSpacing: '-0.02em',
                        background: 'linear-gradient(to right, #fff, #94a3b8)',
                        WebkitBackgroundClip: 'text',
                        WebkitTextFillColor: 'transparent'
                    }}>
                        Master Your Body, <br />
                        <span className="gradient-text">Optimize Your Life</span>
                    </h1>

                    <p style={{
                        fontSize: '1.25rem',
                        color: 'var(--text-secondary)',
                        marginBottom: '40px',
                        lineHeight: 1.6
                    }}>
                        The all-in-one platform to track nutrition, monitor workouts, and visualize your progress with AI-driven insights.
                    </p>

                    <div style={{ display: 'flex', gap: '16px', justifyContent: 'center', flexWrap: 'wrap' }}>
                        <motion.button
                            whileHover={{ scale: 1.05 }}
                            whileTap={{ scale: 0.95 }}
                            style={{
                                background: 'linear-gradient(135deg, var(--color-primary), var(--color-secondary))',
                                color: 'white',
                                padding: '16px 32px',
                                borderRadius: '50px',
                                fontSize: '1rem',
                                fontWeight: 600,
                                display: 'flex',
                                alignItems: 'center',
                                gap: '8px',
                                boxShadow: '0 10px 25px -5px rgba(139, 92, 246, 0.5)'
                            }}
                        >
                            Start Tracking Now <ArrowRight size={20} />
                        </motion.button>

                        <motion.button
                            whileHover={{ scale: 1.05, backgroundColor: 'rgba(255,255,255,0.1)' }}
                            whileTap={{ scale: 0.95 }}
                            style={{
                                background: 'rgba(255, 255, 255, 0.05)',
                                color: 'var(--text-primary)',
                                padding: '16px 32px',
                                borderRadius: '50px',
                                fontSize: '1rem',
                                fontWeight: 600,
                                border: '1px solid var(--glass-border)',
                                backdropFilter: 'blur(10px)'
                            }}
                        >
                            View Demo
                        </motion.button>
                    </div>
                </motion.div>

                {/* Floating Icons Animation */}
                <motion.div
                    animate={{ y: [0, -20, 0] }}
                    transition={{ duration: 4, repeat: Infinity, ease: "easeInOut" }}
                    style={{ position: 'absolute', top: '15%', left: '10%', opacity: 0.6 }}
                >
                    <Apple size={48} color="var(--color-secondary)" />
                </motion.div>
                <motion.div
                    animate={{ y: [0, 20, 0] }}
                    transition={{ duration: 5, repeat: Infinity, ease: "easeInOut", delay: 1 }}
                    style={{ position: 'absolute', bottom: '20%', right: '10%', opacity: 0.6 }}
                >
                    <Activity size={56} color="var(--color-primary)" />
                </motion.div>
            </div>
        </section>
    );
};

export default Hero;
