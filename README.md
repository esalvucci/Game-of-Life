# Game-of-Life
### Assignment-01 PCD1718 Exam
(In italian)

Implementazione concorrente in Java di Game of Life tramite semafori.

## Game of Life

Game of Life o Life è un esempio di automa cellulare sviluppato dal matematico inglese John Conway.

Game of Life è un "gioco" che non necessita di input da parte di alcun "giocatore" (zero-player) se non per avviare il gioco e inizializzare lo stato del "mondo". Questo consiste nell'evoluzione di un mondo, rappresentato da una matrice bidimensionale. Una cella della matrice può essere "accesa" o "spenta", rappresentando rispettivamente lo stato di "viva" o "morta".

Il mondo si evolve autonomamente (da uno stato al suo successivo) rendendo vive o morte le proprie celle, a seconda dello stato delle 8 celle vicine di ciascun elemento della matrice allo stato precedente (per questo motivo non c'è biogno di alcun input da parte di un giocatore).

Lo stato "corrente" di una matrice determina quindi il suo stato "successivo".

L'aggiornamento dello stato di una matrice (e quindi la sua evoluzione) avviene simultaneamente e in un intervallo di tempo discreto, nel quale lo stato t+1 di una cella è determinatoto dallo stato t delle sue celle vicine (diagonalmente, verticalmente e orizzontalmente).

Il cambiamento (o meno) dello stato di una cella in un determinato istante è stabilito da quattro regole:
- Qualsiasi cella viva con meno di due celle vive adiacenti muore, come per effetto d'isolamento;
- Qualsiasi cella viva con due o tre celle vive adiacenti sopravvive alla generazione successiva;
- Qualsiasi cella viva con più di tre celle vive adiacenti muore, come per effetto di sovrappopolazione;
- Qualsiasi cella morta con esattamente tre celle vive adiacenti diventa una cella viva, come per effetto di riproduzione.

La variazione tra una cella accesa in una spenta (o viceversa) può essere interpretata come una semplice rappresentazione del processo di vita/morte, questo giustifica il nome "Game of Life".

Le quattro regole di evoluzione di una cella sono state scelte attentamente da Conway per soddisfare tre criteri fondamentali:

- Non ci deve essere una condizione iniziale per la quale si possa provare che la popolazione possa crescere senza limiti.
- Ci deve essere una condizione iniziale per la quale \textit{apparentemente} la popolazione cresce senza limiti.
- Deve esserci una condizione iniziale per la quale la popolazione cresce e cambia nel tempo prima di estinguersi in tre possibili modi, rappresentati con degli esempi in figura \ref{fig:patterns} :
	
	- Svanire completamente per sovrappopolamento o per una condizione troppo sparsa delle celle della matrice.
  - Entrare in uno stato stabile, che non varia nelle evoluzioni successive.
  - Entrare in uno stato che "oscilla" nel tempo da uno stato ad un altro senza poter entrare in stati diversi.
