A mod that lets you know when your sites are down during the game.

### How does it work?
You add a list of your sites to the configuration in advance and set the frequency of checks. While you are in the world, the check will occur with a periodic frequency. If one or more sites are unavailable, you will receive a notification in the chat, accompanied by a characteristic sound

### Setting up
After the first launch, a `config` folder will appear in your game folder, and in it - a `mc-uptime.json` file. This is where the setting up takes place. Here is an example of how to add your site there:
```json
{
  "sites" : [
    {
      "id" : "Your Site Name",
      "url" : "https://example.com"
    }
  ],
  "cooldown" : 60
}
```
You can add more sites, their number is unlimited:
```json
{
  "sites" : [
    {
      "id" : "Some Site",
      "url" : "https://example.com"
    },
    {
      "id" : "YouTube",
      "url" : "https://youtube.com"
    },
    {
      "id" : "Your Second Site",
      "url" : "https://ur-second-site.net"
    }
  ],
  "cooldown" : 120
}
```
`cooldown` - time between checks in seconds.
Please note that increasing the number of sites increases the overall time required for verification.