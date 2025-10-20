package ru.polovinko.state_machine;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;
import ru.polovinko.state_machine.domain.Event;
import ru.polovinko.state_machine.domain.State;

@Log4j2
@SpringBootApplication
public class StateMachineApplication implements CommandLineRunner {

   @Autowired
   private StateMachine<State, Event> stateMachine;

   public static void main(String[] args) {
      SpringApplication.run(StateMachineApplication.class, args);
   }

   @Override
   public void run(String... args) throws Exception {
      System.out.println("send E1");
      stateMachine.sendEvent(Event.E1);
//      System.out.println("send E2");
//      stateMachine.sendEvent(Event.E2);
      System.out.println("send E3");
      stateMachine.sendEvent(Event.E3);
      System.out.println("send E4");
      stateMachine.sendEvent(Event.E4);
   }
}
