const fs = require('fs');

async function main() {
    // Prefer the common name DISCORD_WEBHOOK (recommended), but allow legacy WEBHOOK_DISCORD as a fallback.
    const webhook = process.env.DISCORD_WEBHOOK || process.env.WEBHOOK_DISCORD;
    const eventPath = process.env.GITHUB_EVENT_PATH;
    const eventName = process.env.GITHUB_EVENT_NAME || 'unknown';
    const repoEnv = process.env.GITHUB_REPOSITORY;

    if (!webhook) {
        console.error('DISCORD_WEBHOOK (or WEBHOOK_DISCORD) env not set. Define a repository secret named DISCORD_WEBHOOK with your Discord incoming webhook URL (or WEBHOOK_DISCORD).');
        process.exit(1);
    }

    if (!eventPath || !fs.existsSync(eventPath)) {
        console.error('GITHUB_EVENT_PATH missing or file does not exist:', eventPath);
        process.exit(1);
    }

    if (typeof fetch === 'undefined') {
        console.error('Global fetch is not available in this Node runtime. Ensure the workflow uses Node 18+ (setup-node@v4 with node-version: 18) or add a fetch polyfill.');
        process.exit(1);
    }

    const raw = fs.readFileSync(eventPath, 'utf8');
    let payload;
    try { payload = JSON.parse(raw); } catch (e) {
        console.error('Failed to parse GITHUB event payload:', e);
        process.exit(1);
    }

    const repoFull = payload.repository?.full_name || repoEnv || 'repository';
    const ts = new Date().toISOString();

    function truncate(s, n = 300) {
        if (!s) return '';
        return s.length > n ? s.slice(0, n - 1) + '…' : s;
    }

    // Basic embed skeleton
    let embed = {
        title: '',
        url: '',
        description: '',
        color: 0x5865F2, // default Discord blurple
        timestamp: ts,
        fields: [],
        author: {
            name: `GitHub • ${repoFull}`
        }
    };

    if (eventName === 'push') {
        const branch = (payload.ref || '').replace('refs/heads/', '');
        const pusher = payload.pusher?.name || payload.sender?.login || process.env.GITHUB_ACTOR;
        const commitCount = (payload.commits || []).length;
        embed.title = `${pusher} pushed ${commitCount} commit${commitCount !== 1 ? 's' : ''} to ${branch}`;
        embed.url = payload.compare || payload.repository?.html_url;
        const commits = (payload.commits || []).slice(0, 10).map(c => `[\`${c.id.slice(0,7)}\`](${c.url}) ${truncate(c.message.split('\n')[0], 120)}`).join('\n');
        embed.description = commits || truncate(payload.head_commit?.message || '', 800);
        embed.fields.push({ name: 'Branch', value: `\`${branch}\``, inline: true });
        embed.fields.push({ name: 'Pusher', value: pusher || 'unknown', inline: true });
    } else if (eventName === 'pull_request') {
        const action = payload.action;
        const pr = payload.pull_request || {};
        embed.title = `Pull request ${action}: ${truncate(pr.title || '', 240)}`;
        embed.url = pr.html_url;
        embed.description = truncate(pr.body || '', 800);
        embed.fields.push({ name: 'Author', value: pr.user?.login || 'unknown', inline: true });
        embed.fields.push({ name: 'PR', value: `#${pr.number} • ${pr.state}`, inline: true });
        embed.fields.push({ name: 'Base → Head', value: `${pr.base?.ref || '?'} → ${pr.head?.ref || '?'}`, inline: true });
        embed.color = action === 'closed' && pr.merged ? 0x2ecc71 : (action === 'closed' ? 0xe74c3c : 0x3498db);
    } else if (eventName === 'release') {
        const rel = payload.release || {};
        embed.title = `Release published: ${rel.tag_name || rel.name || ''}`;
        embed.url = rel.html_url;
        embed.description = truncate(rel.body || '', 1000);
        embed.fields.push({ name: 'Author', value: rel.author?.login || 'unknown', inline: true });
        embed.color = 0x9b59b6;
    } else if (eventName === 'workflow_run') {
        const wr = payload.workflow_run || {};
        embed.title = `Workflow ${wr.name || ''} ${wr.conclusion || wr.status || ''}`;
        embed.url = wr.html_url;
        embed.description = `Run by ${wr.actor?.login || 'unknown'} on \`${wr.head_branch || ''}\``;
        embed.color = wr.conclusion === 'success' ? 0x2ecc71 : 0xe74c3c;
    } else if (eventName === 'issues') {
        const action = payload.action;
        const issue = payload.issue || {};
        embed.title = `Issue ${action}: ${truncate(issue.title || '', 240)}`;
        embed.url = issue.html_url;
        embed.description = truncate(issue.body || '', 1000);
        const labels = (issue.labels || []).map(l => (l.name || (typeof l === 'string' ? l : 'label'))).join(', ') || 'none';
        const assignee = issue.assignee?.login || 'none';
        embed.fields.push({ name: 'Author', value: issue.user?.login || 'unknown', inline: true });
        embed.fields.push({ name: 'Issue', value: `#${issue.number} • ${issue.state}`, inline: true });
        embed.fields.push({ name: 'Assignee', value: assignee, inline: true });
        embed.fields.push({ name: 'Labels', value: labels, inline: false });
        // color per action
        if (action === 'opened' || action === 'reopened') embed.color = 0x2ecc71;
        else if (action === 'closed') embed.color = 0xe74c3c;
        else if (action === 'labeled') embed.color = 0xf1c40f;
        else if (action === 'edited') embed.color = 0x3498db;
        else embed.color = 0x95a5a6;
    } else {
        embed.title = `${repoFull} — ${eventName}`;
        embed.description = `Event received.`;
        embed.fields.push({ name: 'Quick payload', value: '```json\n' + truncate(JSON.stringify(payload, null, 2), 1000) + '\n```' });
    }

    const body = {
        username: `GitHub • ${payload.repository?.name || repoFull}`,
        embeds: [embed]
    };

    try {
        const res = await fetch(webhook, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        });
        if (!res.ok) {
            const text = await res.text();
            console.error('Discord webhook returned non-OK:', res.status, text);
            process.exit(1);
        } else {
            console.log('Discord notification sent (event:', eventName + ')');
        }
    } catch (err) {
        console.error('Failed to send to Discord webhook:', err);
        process.exit(1);
    }
}

main();