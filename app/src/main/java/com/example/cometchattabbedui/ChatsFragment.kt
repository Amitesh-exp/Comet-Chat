package com.example.cometchattabbedui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import com.cometchat.chat.constants.CometChatConstants
import com.cometchat.chat.models.Conversation
import com.cometchat.chat.models.Group
import com.cometchat.chat.models.User
import com.cometchat.chatuikit.conversations.CometChatConversations

class ChatsFragment : Fragment() {

    private lateinit var conversationsView: CometChatConversations

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setListeners()
    }

    private fun initView() {
        conversationsView = requireView().findViewById(R.id.cometChatConversations)
    }

    private fun setListeners() {
        conversationsView.setOnItemClick { _, _, conversation ->
            startMessageActivity(conversation)
        }
    }

    private fun startMessageActivity(conversation: Conversation) {

        Log.d("ChatsFragment", "Conversation clicked: ${conversation.conversationType}, with: ${conversation.conversationWith}")

        val intent = Intent(requireContext(), MessageActivity::class.java).apply {
            when (conversation.conversationType) {
                CometChatConstants.CONVERSATION_TYPE_GROUP -> {
                    val group = conversation.conversationWith as? Group
                    if (group != null) {
                        Log.d("ChatsFragment", "Starting group chat with ${group.guid}")
                        putExtra("guid", group.guid)
                    } else {
                        Log.e("ChatsFragment", "conversationWith is not a Group")
                    }
                }
                else -> {
                    val user = conversation.conversationWith as User
                    if (user != null) {
                        Log.d("ChatsFragment", "Starting user chat with ${user.uid}")
                        putExtra("uid", user.uid)
                    } else {
                        Log.e("ChatsFragment", "conversationWith is not a User")
                    }
                }
            }
        }
        startActivity(intent)
    }
}