・Descriptions are available in both Japanese and English.
・日本語と英語の説明があります。

aws-low-cost-gpt-client:Web client app. Vue, Quasar, JavaScript, html, css.

aws-low-cost-gpt-dummy-api:Dummy web api. Spring boot, Java. 


---
This project is a web construction project that utilizes generative AI at an extremely low cost.

Many sites offering generative AI services are subscription-based, and while APIs are pay-per-use, self-hosting is required for external access. There are ChatGPT-like website projects on GitHub, but most require EC2 or Fargate.

Here's my rough annual infrastructure cost calculation:
- Cost of EC2 : `$20 * 12 = $240 = 36,000 yen`
- Cost of Fargate : `$43 * 12 = $516 = 77,400 yen`
- My idea : `$0.5 * 12 = $6 = 900 yen`

In reality, you need to add the usage fee for the OpenAI API.
- Cost of OpenAI API : `$15.75 * 12 = $189 = 28,350 yen`

Using EC2 or Fargate is more expensive than using an Open AI subscription.
- Subscription OpenAI: `$20 * 12 = $240 = 36,000 yen`

My plan allows for minimizing infrastructure costs, so the actual cost is only the API fee.
OpenAI API is expensive, but Google's Gemini pro is about one-tenth the cost, so I expect OpenAI API prices to drop as market competition intensifies...

Now, here's an overview of the project. It's structured as follows:
- S3: Static content (Html, JavaScript, CSS, Vue, Quasar)
- Cognito: Authentication process (JavaScript)
- API Gateway, Lambda: (Java or Python, external service integration)
- DynamoDB: Storing user information, chat history

By the way, I am a Java server-side engineer, but this is my first time with AWS and I am almost a novice in web front-end.

If there are any gods out there who can help with development, please help!!
I hope to implement text generation functionality around March 1, 2024.

---

このプロジェクトは極めて低コストで生成AIを利用するウェブサイト構築プロジェクトです。

生成AIサービスを提供しているサイトはサブスクリプションが多く、APIは従量課金ですが野外からアクセスする場合、セルフホストしなければなりません。GitHubにはChatGPTを模したWebサイトプロジェクトがありますが、大抵の場合はEC2やFargateが必要でした。

私の大雑把な年間インフラコスト計算はこうです。
- EC2のコスト    : `$20 * 12 = $240  = 36000円`
- Fargateのコスト: `$43 * 12 = $516  = 77400円`
- 私のアイディア : `$0.5* 12 = $6    = 900円`

実際にはこれらにOpenAI APIの利用料をプラスする必要があります。
- OpenAI APIのコスト: `$15.75 * 12 = $189 = 28350円`

EC2やFargateを使うよりはOpen AIのサブスクを使った方が安いです。
- サブスクOpenAI : `$20 * 12 = $240  = 36000円`

私のプランではインフラコストを可能な限り抑えることができるため、実質的な利用料金はAPIの費用のみです。
OpenAI APIの場合は高いですがGoogleのGemini proは10分の1くらいなので、市場競争が激しくなればOpenAI API価格も下がると見込んでいます…。

さて、プロジェクトの概要です。以下の構成にしています。
- S3：静的コンテンツ（Html, JavaScript, CSS, Vue, Quasar）
- Cognito：認証処理（JavaScript）
- API Gateway、Lambda：(Java or python, 外部サービス連携)
- DynamoDB：ユーザー情報、Chat履歴の格納

ちなみに私はJavaのサーバーサイドエンジニアですがAWSは初めてですし、Webの画面周りは素人に近いです。

開発を手伝ってくださる神がいたら助けてください！！
2024年3月1日ごろにはテキスト生成機能を実装したいと思っています。
---

![Architecture](aws-low-cost-gpt-client/Architecture/2024-01-31%20021414.png)
