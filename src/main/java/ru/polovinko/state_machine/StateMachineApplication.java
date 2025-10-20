package ru.polovinko.state_machine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;
import ru.polovinko.state_machine.domain.Event;
import ru.polovinko.state_machine.domain.State;

@SpringBootApplication
public class StateMachineApplication implements CommandLineRunner {

   @Autowired
   private StateMachine<State, Event> stateMachine;

   public static void main(String[] args) {
      SpringApplication.run(StateMachineApplication.class, args);
   }

   @Override
   public void run(String... args) throws Exception {
      stateMachine.sendEvent(Event.E1);
      stateMachine.sendEvent(Event.E2);
   }
}
