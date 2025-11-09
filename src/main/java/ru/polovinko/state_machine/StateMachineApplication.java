package ru.polovinko.state_machine;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import reactor.core.publisher.Mono;
import ru.polovinko.state_machine.domain.Application;
import ru.polovinko.state_machine.domain.ContextObject;
import ru.polovinko.state_machine.domain.SmEvent;
import ru.polovinko.state_machine.domain.SmState;
import ru.polovinko.state_machine.service.CurrentStateMachineHolder;

import java.time.*;

import static java.time.temporal.ChronoUnit.DAYS;

@Log4j2
@SpringBootApplication
public class StateMachineApplication implements CommandLineRunner {

   @Autowired
   private CurrentStateMachineHolder stateMachineHolder;

   @Autowired
   private StateMachinePersister<SmState, SmEvent, ContextObject> persister;

   public static void main(String[] args) {
      SpringApplication.run(StateMachineApplication.class, args);
   }

   @Override
   public void run(String... args) throws Exception {

      System.out.println("Current state: " + stateMachineHolder.getStateMachine().getState().getId());
      System.out.println("send E1");
      stateMachineHolder.getStateMachine().sendEvent(SmEvent.E1);

      System.out.println("Current state: " + stateMachineHolder.getStateMachine().getState().getId());

      System.out.println("Сохраняем СМ");
      Application application = new Application();
      persister.persist(stateMachineHolder.getStateMachine(), application);

      System.out.println("send E2");
      stateMachineHolder.getStateMachine().sendEvent(SmEvent.E2);
      System.out.println("Current state: " + stateMachineHolder.getStateMachine().getState().getId());

      System.out.println("send E3");
      stateMachineHolder.getStateMachine().sendEvent(SmEvent.E3);
      System.out.println("Current state: " + stateMachineHolder.getStateMachine().getState().getId());

      System.out.println("Восстанавливаем СМ");
      persister.restore(stateMachineHolder.getStateMachine(), application);
      System.out.println("Current state: " + stateMachineHolder.getStateMachine().getState().getId());

      System.out.println("send E4");
      stateMachineHolder.getStateMachine().sendEvent(SmEvent.E4);
      System.out.println("Current state: " + stateMachineHolder.getStateMachine().getState().getId());
   }
}
