console.log("✅ stripe.js が読み込まれました！");

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("checkout-form");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const planId = document.querySelector('input[name="planId"]:checked').value;

    const res = await fetch("/api/stripe/create-checkout-session", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ planId })
    });

    console.log("レスポンスコード:", res.status);
    if (!res.ok) {
      const errorText = await res.text();
      console.error("レスポンスエラー:", errorText);
      return;
    }

    


    const { sessionId } = await res.json();

    if (!sessionId) {
      alert("sessionIdが取得できませんでした");
      return;
    }

    const stripe = Stripe(document.body.dataset.stripeKey);
    console.log('STRIPE KEY : ' + stripe);
    stripe.redirectToCheckout({ sessionId });
  });
});
