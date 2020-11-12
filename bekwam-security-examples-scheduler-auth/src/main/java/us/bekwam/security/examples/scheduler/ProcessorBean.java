package us.bekwam.security.examples.scheduler;

import javax.annotation.security.RunAs;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

@Singleton
@RunAs("sys")
public class ProcessorBean {

    @Inject
    private FormBean formBean;

    @Lock(LockType.READ)
    @Schedule(minute = "*/2", hour = "*", persistent = false)
    public void processForms() throws InterruptedException {
        formBean.processForms();
    }
}
