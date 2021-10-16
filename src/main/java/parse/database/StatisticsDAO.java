package parse.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import parse.Statistics;

public class StatisticsDAO implements DAOInterface<Statistics> {
    private Session currentSession;
    private Transaction currentTransaction;
    private static SessionFactory sessionFactory;

    StatisticsDAO(){
        sessionFactory = SessionFactoryBuilder.getSessionFactory();
    }

    public Session getCurrentSession() {
        return this.currentSession;
    }

    public void openCurrentSessionWithTransaction() {
        this.currentSession = sessionFactory.openSession();
        this.currentTransaction = currentSession.beginTransaction();
    }

    public void closeCurrentSessionWithTransaction() {
        this.currentTransaction.commit();
        this.currentSession.close();
    }

    @Override
    public void save(Statistics s) {
        this.openCurrentSessionWithTransaction();
        getCurrentSession().save(s);
        this.closeCurrentSessionWithTransaction();
    }
}
