# CombatLog
CombatLog é um plugin <b>simples</b> e de <b>fácil</b> configuração, tem como função gerenciar os combates em seu servidor.

## Configuração
```yaml
# Algumas configurações basica.
settings:
  # Tempo para expirar o combate (em segundos)
  expire-time: 20

# Caso queira que staffs não entrem em combate deixe a opção abaixo em true
staff-bypass:
  enable: false
  # Permissão necessaria para ignorar o combate 
  permission: "combatlog.bypass"

messages:

  # Opções de replace:
  # $newline - pula uma linha.
  # $enemy - nome do inimigo morto.
  dead-enemy: "&aSeu inimigo foi eliminado!"

  # Opções de replace:
  # $newline - pula uma linha.
  teleport-not-allowed: "&cVocê não pode teletransportar em combate!"

  # Opções de replace:
  # $newline - pula uma linha.
  expired: "&aVocê saiu de combate!"

  # Opções de replace:
  # $newline - pula uma linha.
  # $enemy - nome do inimigo em combate.
  # $player - nome do jogador em combate.
  # $time - tempo par sair de combate.
  in-combat: "&c$time segundos para sair do combate com $enemy"

# Sistema simples de punição caso o jogador deslogue em combate essa função será chamada
punish:
  # Caso esteja true o jogador morrerá ao deslogar em combate
  enable: true
  # Essa mensagem será enviada para todos jogadores online
  #
  # Opções de replace:
  # $newline - pula uma linha.
  # 7$player - nome do inimigo morto.
  message: "&7$player &cdesconectou em combate! que feio."

# Lista de comandos que será bloqueado
commands-block:
  enable: true
  message: "&cVocê não pode usar esse comando em combate!"
  list:
    - '/spawn'

# Lista de mundos aonde o plugin irá funcionar
# Se o enable estiver false ele funcionará em todos mundos
worlds-block:
  enable: true
  list:
    - 'world'
```

## Tecnologias utilizadas
O Projeto foi desenvolvido utilizando as seguintes tecnologias.

- [command-framework]() - framework para criação e gerenciamento de comandos.
- [configuration-inject](https://github.com/knightzmc/pdm) - injetar valores de configurações automaticamente.

## Download
Você pode baixar o plugin clicando [AQUI](https://github.com/BADnotice/CombatLog/releases) ou se preferir alterar, pode clonar o repositório.

## API
O plugin foi desenvolvido com uma API adequada e robusta para desenvolvedores <br>
você pode acessa-la clicando [AQUI](https://github.com/BADnotice/CombatLog/releases)

## Events
- <b>CombatPunishEvent</b> - Chamado quando um jogador desloga em combate.
- <b>CombatTagEvent</b> - Chamado quando um combate é iniciado.
- <b>CombatUnTagEvent</b> - Chamado quando o um combate é finalizado.
```java
  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onCombatPunish(CombatPunishEvent event) {
         Player player = event.getPlayer();
         Combat combat = event.getCombat();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                   onlinePlayer.sendMessage("§e" +  player.getName() + " deslogou em combate, que feio");
               }
    }
```
```java
  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onCombatTag(CombatTagEvent event) {
       Player damager = event.getDamager();
       Player damagee = event.getDamagee();

       Bukkit.broadcastMessage("§e" + damaer.getName() + " e " + damagee.getName() + " entraram em combate");
    }
```
```java
 @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onCombatUnTag(CombatUnTagEvent event) {
        Combat combat = event.getCombat();

       Bukkit.broadcastMessage("§e" + combat.getPlayerName() + " e " + getEnemyName() + " sairam do combate");
  }