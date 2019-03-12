package com.foo;

import com.foo.model.Event;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Iterator;
import java.util.List;

/**
 * @author JasonLin
 * @version V1.0
 * @date 2019/3/11
 */
public class Main {

    public static void main(String[] args) {
        SessionFactoryUtil sessionFactoryUtil = new SessionFactoryUtil();
        SessionFactory sessionFactory = sessionFactoryUtil.getSessionFactory();

        //1.查询单个实体
        /*doExecute(sessionFactory, new HibernateExecuteCallBack() {
            @Override
            public void execute(Session session) {
                Event event = session.get(Event.class, 7L);
                System.out.println(event);
            }
        });*/
        //2.查询单个实体
        /*doExecute(sessionFactory, new HibernateExecuteCallBack() {
            @Override
            public void execute(Session session) {
                Event event = session.get(Event.class, 7L);
                System.out.println(event);
            }
        });*/
        //3.查询集合
        doExecute(sessionFactory, new HibernateExecuteCallBack() {
            @Override
            public void execute(Session session) {
                testIterateQuery(session,"from Event where id>=7");
            }
        });
        doExecute(sessionFactory, new HibernateExecuteCallBack() {
            @Override
            public void execute(Session session) {
                testIterateQuery(session,"from Event where id>=7");
            }
        });
        //4.查询集合
        /*doExecute(sessionFactory, new HibernateExecuteCallBack() {
            @Override
            public void execute(Session session) {
                testListQuery(session, "from Event where id>=7");
            }
        });
        doExecute(sessionFactory, new HibernateExecuteCallBack() {
            @Override
            public void execute(Session session) {
                testListQuery(session, "from Event where id>=7");
            }
        });*/
    }

    private static void testIterateQuery(Session session, String testHql) {
        Query query = session.createQuery(testHql);
        query.setCacheable(true);
        @SuppressWarnings("unchecked")
        Iterator<Event> list = (Iterator<Event>) query.iterate();
        while (list.hasNext()) {
            System.out.println("iterate语句测试query cache:" + list.next());
        }
    }

    private static void testListQuery(Session session, String testHql) {
        Query query = session.createQuery(testHql);
        //必须开启查询缓存并且，手动设置缓存为true，查询缓存才生效
        query.setCacheable(true);
        @SuppressWarnings("unchecked")
        List<Event> list = query.list();
        for (Event event : list) {
            System.out.println("list语句测试query cache:" + event);
        }
    }


    public static void doExecute(SessionFactory sessionFactory, HibernateExecuteCallBack executeCallBack) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        executeCallBack.execute(session);

        session.getTransaction().commit();
        session.close();
    }

    interface HibernateExecuteCallBack {
        void execute(Session session);
    }
}
