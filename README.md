<h1 align="center">📚 BookMap</h1>

<p align="center">
Um ecossistema completo para leitores que permite explorar o vasto catálogo de obras em domínio público da API Gutenberg.
</p>

<p align="center">
O projeto foi desenvolvido com foco em <b>escalabilidade, testabilidade e performance</b>, aplicando os padrões mais modernos de desenvolvimento Android.
</p>

<hr>

<h2>🚀 Principais Funcionalidades</h2>

<ul>
<li><b>Exploração Inteligente:</b> Integração com a API Gutendex para consulta de milhares de obras em domínio público.</li>

<li><b>Navegação Fluida:</b> Sistema de listagem com <b>paginação</b> para otimização do tráfego de dados e uso de memória.</li>

<li><b>Gestão de Biblioteca:</b> Sistema de favoritos e controle de status de leitura (<i>Lido</i>, <i>Lendo</i>, <i>Quero Ler</i>).</li>

<li><b>Segurança e Personalização:</b> Autenticação robusta com <b>Firebase Authentication</b>.</li>
</ul>

<hr>

<h2>🏗️ Arquitetura e Engenharia</h2>

<p>
O projeto segue os princípios da <b>Clean Architecture</b>, garantindo separação clara entre lógica de negócio,
fontes de dados e interface de usuário.
</p>

<h3>Camadas do Sistema</h3>

<ol>
<li>
<b>Data</b><br>
Implementação do padrão <b>Repository</b>, responsável por gerenciar as fontes de dados remotas
(Retrofit) e locais.
</li>

<li>
<b>Domain</b><br>
Contém as regras de negócio puras e os <b>Use Cases</b>, facilitando manutenção e testes unitários.
</li>

<li>
<b>Presentation</b><br>
Implementação híbrida de <b>MVVM + MVI</b>.  
O uso de MVI garante um estado de UI previsível e um fluxo de dados unidirecional.
</li>
</ol>

<hr>

<h2>🛠️ Stack Tecnológica</h2>

<ul>
<li><b>Linguagem:</b> Kotlin (Coroutines + Flow)</li>
<li><b>UI:</b> Jetpack Compose</li>
<li><b>Networking:</b> Retrofit + OkHttp para consumo de APIs REST</li>
<li><b>Injeção de Dependência:</b> Hilt</li>
<li><b>Persistência e Autenticação:</b> Firebase (Authentication + Firestore)</li>
</ul>

<hr>

<h2>🎯 Destaques Técnicos</h2>

<blockquote>
<b>BookMap não é apenas um app de consulta.</b><br><br>
Ele funciona como um estudo de caso sobre como lidar com estados complexos de UI
e sincronização em tempo real.<br><br>
A transição entre <b>MVVM</b> e <b>MVI</b> foi escolhida para mitigar efeitos colaterais
e garantir uma experiência consistente para o usuário, mesmo em condições de rede instáveis.
</blockquote>

<hr>

<p align="center">
Feito com ❤️ usando Kotlin e as melhores práticas do ecossistema Android.
</p>
