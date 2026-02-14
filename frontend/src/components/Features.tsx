import { motion } from 'framer-motion';
import { Utensils, Activity, Droplets, Sparkles, PieChart, Bell } from 'lucide-react';

const featureList = [
    {
        icon: <Utensils size={32} color="#ec4899" />,
        title: "Smart Nutrition",
        desc: "Log meals instantly with our extensive database. Track macros, calories, and micronutrients with precision."
    },
    {
        icon: <Activity size={32} color="#8b5cf6" />,
        title: "Workout Analytics",
        desc: "Visualize your fitness journey. Track reps, sets, and progress over time with detailed charts."
    },
    {
        icon: <Droplets size={32} color="#06b6d4" />,
        title: "Hydration Tracking",
        desc: "Never miss a sip. Set goals and get smart reminders to keep your hydration levels optimal."
    },
    {
        icon: <Sparkles size={32} color="#f59e0b" />,
        title: "AI Insights",
        desc: "Receive personalized recommendations and health tips tailored to your unique lifestyle and goals."
    },
    {
        icon: <PieChart size={32} color="#10b981" />,
        title: "Comprehensive Reports",
        desc: "Generate PDF reports to share with your nutritionist or trainer. Analyze trends and patterns."
    },
    {
        icon: <Bell size={32} color="#ef4444" />,
        title: "Smart Reminders",
        desc: "Stay consistent with customizable reminders for workouts, meals, and water intake."
    }
];

const Features = () => {
    return (
        <section id="features" style={{ padding: '100px 20px', background: 'var(--bg-secondary)' }}>
            <div className="container" style={{ maxWidth: '1200px', margin: '0 auto' }}>
                <div style={{ textAlign: 'center', marginBottom: '60px' }}>
                    <h2 className="gradient-text" style={{ fontSize: '2.5rem', marginBottom: '16px' }}>
                        Everything You Need <br /> To succeed
                    </h2>
                    <p style={{ color: 'var(--text-secondary)', fontSize: '1.2rem', maxWidth: '600px', margin: '0 auto' }}>
                        Powerful tools designed to help you achieve your health and fitness goals faster.
                    </p>
                </div>

                <div style={{
                    display: 'grid',
                    gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))',
                    gap: '32px'
                }}>
                    {featureList.map((feature, index) => (
                        <motion.div
                            key={index}
                            initial={{ opacity: 0, y: 20 }}
                            whileInView={{ opacity: 1, y: 0 }}
                            viewport={{ once: true }}
                            transition={{ delay: index * 0.1 }}
                            whileHover={{ y: -5 }}
                            className="glass"
                            style={{
                                padding: '32px',
                                borderRadius: '24px',
                                background: 'rgba(255, 255, 255, 0.03)',
                                border: '1px solid var(--glass-border)'
                            }}
                        >
                            <div style={{
                                background: 'rgba(255, 255, 255, 0.05)',
                                width: '64px',
                                height: '64px',
                                borderRadius: '16px',
                                display: 'flex',
                                alignItems: 'center',
                                justifyContent: 'center',
                                marginBottom: '24px'
                            }}>
                                {feature.icon}
                            </div>
                            <h3 style={{ fontSize: '1.5rem', marginBottom: '12px', color: 'var(--text-primary)' }}>
                                {feature.title}
                            </h3>
                            <p style={{ color: 'var(--text-secondary)', lineHeight: 1.6 }}>
                                {feature.desc}
                            </p>
                        </motion.div>
                    ))}
                </div>
            </div>
        </section>
    );
};

export default Features;
