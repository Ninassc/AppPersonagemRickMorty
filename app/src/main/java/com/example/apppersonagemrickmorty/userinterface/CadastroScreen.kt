package com.example.apppersonagemrickmorty.userinterface

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apppersonagemrickmorty.database.PersonagemDatabase
import com.example.apppersonagemrickmorty.database.UsuarioEntity
import com.example.apppersonagemrickmorty.repository.UsuarioRepository
import kotlinx.coroutines.launch

@Composable
fun CadastroScreen(
    onCadastroSucesso: () -> Unit,
    onVoltarParaLogin: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repository = remember {
        UsuarioRepository(PersonagemDatabase.getInstance(context).usuarioDao())
    }

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }

    var mensagemErro by remember { mutableStateOf<String?>(null) }
    var estaCarregando by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
            .padding(horizontal = 24.dp, vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE2ECF0))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, bottom = 20.dp, start = 16.dp, end = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Cadastro",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF1E1E1E)
                )

                Spacer(Modifier.height(20.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFCCDCE3))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextField(
                            value = nome,
                            onValueChange = { nome = it },
                            label = { Text("Nome de usuário") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            )
                        )

                        Spacer(Modifier.height(12.dp))

                        TextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Endereço de e-mail") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            )
                        )

                        Spacer(Modifier.height(12.dp))

                        TextField(
                            value = senha,
                            onValueChange = { senha = it },
                            label = { Text("Senha") },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            )
                        )

                        Spacer(Modifier.height(12.dp))

                        TextField(
                            value = confirmarSenha,
                            onValueChange = { confirmarSenha = it },
                            label = { Text("Confirme a senha") },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            )
                        )

                        Spacer(Modifier.height(24.dp))

                        mensagemErro?.let { erro ->
                            Text(
                                text = erro,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center
                            )
                            Spacer(Modifier.height(12.dp))
                        }

                        Button(
                            onClick = {
                                if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
                                    mensagemErro = "Preencha todos os campos!"
                                    return@Button
                                }
                                if (senha != confirmarSenha) {
                                    mensagemErro = "As senhas não coincidem!"
                                    return@Button
                                }

                                scope.launch {
                                    estaCarregando = true
                                    mensagemErro = null
                                    val sucesso = repository.cadastrar(
                                        UsuarioEntity(name = nome, email = email, senha = senha)
                                    )
                                    estaCarregando = false
                                    if (sucesso) {
                                        onCadastroSucesso()
                                    } else {
                                        mensagemErro = "E-mail já cadastrado!"
                                    }
                                }
                            },
                            enabled = !estaCarregando,
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5A758D)),
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(48.dp)
                        ) {
                            if (estaCarregando) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    strokeWidth = 2.dp,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else {
                                Text("Cadastre-se", fontSize = 16.sp, color = Color.White)
                            }
                        }

                        Spacer(Modifier.height(8.dp))

                        TextButton(onClick = onVoltarParaLogin) {
                            Text(
                                text = "já possui conta?\nfaça login aqui",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF5A758D)
                            )
                        }
                    }
                }
            }
        }
    }
}