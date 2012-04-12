//mail
assert mail
mail.send sender: params.sender, 
	to: "recipient@somecompany.com",
	subject: "Hello",
	textBody: "Hello, how are you doing? -- MrG"
