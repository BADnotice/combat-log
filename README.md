# CombatLog
CombatLog é um plugin <b>simples</b> e de <b>fácil</b> configuração, tem como função gerenciar os combates em seu servidor.

## Tecnologias utilizadas
O Projeto foi desenvolvido utilizando as seguintes tecnologias.

- [command-framework](https://github.com/SaiintBrisson/command-framework) - framework para criação e gerenciamento de comandos.
- [configuration-inject](https://github.com/knightzmc/pdm) - injetar valores de configurações automaticamente.

## Download
Você pode baixar o plugin clicando [AQUI](https://github.com/BADnotice/combat-log/releases) ou se preferir alterar, pode clonar o repositório.

## API
O plugin foi desenvolvido com uma API adequada e robusta para desenvolvedores <br>
você pode acessa-la clicando [AQUI](https://github.com/BADnotice/combat-log/blob/master/src/main/java/io/github/badnotice/combatlog/api/CombatLogAPI.java)

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
