package com.caregiver.gocares.views.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.caregiver.gocares.databinding.WelcomeBinding
import com.github.florent37.viewanimator.ViewAnimator

class WelcomeActivity : AppCompatActivity() {
	private var binding: WelcomeBinding? = null
	var i: Intent? = null
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = WelcomeBinding.inflate(layoutInflater)
		val view: View = binding!!.root
		setContentView(view)
		binding!!.buttonlog.setOnClickListener { startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java).also { i = it }) }
		binding!!.buttonreg.setOnClickListener { startActivity(Intent(this@WelcomeActivity, RegActivity::class.java).also { i = it }) }
	}

	override fun onResume() {
		super.onResume()
		ViewAnimator
						.animate(binding!!.grafis)
						.translationX(-200f, 0f)
						.alpha(0f, 1f)
						.duration(700)
						.start()
	}
}